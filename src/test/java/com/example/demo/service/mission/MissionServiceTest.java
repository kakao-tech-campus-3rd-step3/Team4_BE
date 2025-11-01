package com.example.demo.service.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.admin.domain.MissionPromotion;
import com.example.demo.admin.service.MissionPromotionRepository;
import com.example.demo.config.MissionMinMaxCacheInitializer;
import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.service.EmotionRepository;
import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.MissionErrorCode;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.controller.dto.MissionResponse;
import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import com.example.demo.mission.custom.service.CustomMissionRepository;
import com.example.demo.mission.custom.service.CustomMissionService;
import com.example.demo.mission.regular.infrastructure.jpa.MissionCountEmbeddable;
import com.example.demo.mission.regular.infrastructure.jpa.MissionScoreEmbeddable;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionEntity;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionJpaRepository;
import com.example.demo.mission.regular.service.recommend.MissionRecommendService;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
public class MissionServiceTest {

    @Autowired
    private MissionRecommendService missionRecommendService;

    @Autowired
    private CustomMissionService customMissionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private CustomMissionRepository customMissionRepository;

    @Autowired
    private MissionPromotionRepository missionPromotionRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MissionMinMaxCacheInitializer cacheInitializer;

    @Autowired
    private RegularMissionJpaRepository regularMissionJpaRepository;

    private User testUser;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        testUser = userRepository.save(new User("test@example.com", "TestUser"));
        anotherUser = userRepository.save(new User("another@example.com", "AnotherUser"));

        Emotion emotion = new Emotion(testUser.getId(), 30, 50, 60, 70, 80, 40, 3.0,
            3.0);
        emotionRepository.save(emotion);

        createTestRegularMission("DAILY 미션 1 (Sentiment 높음)", MissionCategoryEnum.DAILY, 8, 5, 5, 5,
            5, 5, 1);
        createTestRegularMission("DAILY 미션 2 (Sentiment 높음)", MissionCategoryEnum.DAILY, 8, 5, 5, 5,
            5, 5, 1);
        createTestRegularMission("DAILY 미션 3 (Sentiment 낮음)", MissionCategoryEnum.DAILY, 2, 5, 5, 5,
            5, 5, 1);
        createTestRegularMission("REFRESH 미션 1 (Sentiment 높음)", MissionCategoryEnum.REFRESH, 9, 6,
            6, 6, 6, 6, 2);
        createTestRegularMission("REFRESH 미션 2 (Sentiment 높음)", MissionCategoryEnum.REFRESH, 9, 6,
            6, 6, 6, 6, 2);
        createTestRegularMission("REFRESH 미션 3 (Sentiment 낮음)", MissionCategoryEnum.REFRESH, 1, 6,
            6, 6, 6, 6, 2);
        createTestRegularMission("EMPLOYMENT 미션 1", MissionCategoryEnum.EMPLOYMENT, 5, 5, 8, 5, 5,
            9, 3);
        createTestRegularMission("EMPLOYMENT 미션 2", MissionCategoryEnum.EMPLOYMENT, 5, 5, 7, 5, 5,
            8, 3);

        entityManager.flush();
        entityManager.clear();

        cacheInitializer.initializeCache();
    }

    private RegularMissionEntity createTestRegularMission(
        String content, MissionCategoryEnum category,
        int sentiment, int energy, int cognitive, int relationship, int stress, int employment,
        int level) {

        MissionScoreEmbeddable scores = new MissionScoreEmbeddable(sentiment, energy, cognitive,
            relationship, stress, employment);
        MissionCountEmbeddable counts = new MissionCountEmbeddable();

        RegularMissionEntity mission = new RegularMissionEntity(null, content, category, level,
            scores, counts, new ArrayList<>());
        return regularMissionJpaRepository.save(mission);
    }

    @Test
    @DisplayName("사용자 감정 기반 추천 미션 조회 성공")
    void getRecommendedMissions_success() {
        List<MissionResponse> recommendedMissions = missionRecommendService.getRecommendedMissions(
            testUser);

        assertThat(recommendedMissions).isNotNull().hasSize(6);

        boolean containsHighSentimentDaily = recommendedMissions.stream()
            .filter(m -> m.getCategory() == MissionCategoryEnum.DAILY)
            .anyMatch(m -> m.getContent().contains("Sentiment 높음"));

        assertThat(containsHighSentimentDaily).isTrue();

        System.out.println("추천된 미션 목록:");
        recommendedMissions.forEach(
            m -> System.out.println("- " + m.getCategory() + ": " + m.getContent()));
    }

    @Test
    @DisplayName("커스텀 미션 생성 성공 및 관리자 승격 대기 상태 확인")
    void createCustomMission_success() {
        String content = "매일 아침 스트레칭 10분 하기";
        MissionCategoryEnum category = MissionCategoryEnum.DAILY;

        CustomMission createdMission = customMissionService.create(content, category, testUser);

        assertThat(createdMission).isNotNull();
        assertThat(createdMission.getId()).isNotNull();
        assertThat(createdMission.getContent()).isEqualTo(content);
        assertThat(createdMission.getCategory()).isEqualTo(category);
        assertThat(createdMission.getUserId()).isEqualTo(testUser.getId());

        Optional<CustomMission> foundMissionOpt = customMissionRepository.findById(
            createdMission.getId());
        assertThat(foundMissionOpt).isPresent();
        assertThat(foundMissionOpt.get().getContent()).isEqualTo(content);

        entityManager.flush();
        entityManager.clear();

        List<MissionPromotion> promotions = missionPromotionRepository.findAllByState(
                org.springframework.data.domain.Pageable.unpaged(), CustomMissionStateEnum.WAITING)
            .getContent();
        Optional<MissionPromotion> foundPromotionOpt = promotions.stream()
            .filter(p -> p.getContent().equals(content) && p.getCategory().equals(category))
            .findFirst();

        assertThat(foundPromotionOpt).isPresent();
        assertThat(foundPromotionOpt.get().getState()).isEqualTo(CustomMissionStateEnum.WAITING);
    }

}

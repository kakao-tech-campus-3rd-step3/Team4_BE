package com.example.demo.service.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.admin.domain.MissionPromotion;
import com.example.demo.admin.service.MissionPromotionRepository;
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
import com.example.demo.mission.regular.service.recommend.MissionRecommendService;
import com.example.demo.plan.service.PlanRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import jakarta.persistence.EntityManager;
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
    private PlanRepository planRepository;

    @Autowired
    private EntityManager entityManager;

    private User testUser;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        testUser = userRepository.save(new User("test@example.com", "TestUser"));
        anotherUser = userRepository.save(new User("another@example.com", "AnotherUser"));

        Emotion emotion = new Emotion(testUser.getId(), 50, 50, 50, 50, 50, 50, 3.0, 3.0);
        emotionRepository.save(emotion);
    }

    @Test
    @DisplayName("사용자 감정 기반 추천 미션 조회 성공")
    void getRecommendedMissions_success() {
        List<MissionResponse> recommendedMissions = missionRecommendService.getRecommendedMissions(
            testUser);

        assertThat(recommendedMissions).isNotNull();
        assertThat(recommendedMissions).hasSize(6);
        recommendedMissions.forEach(mission -> {
            assertThat(mission.getId()).isNotNull();
            assertThat(mission.getContent()).isNotBlank();
            assertThat(mission.getCategory()).isNotNull();
        });
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

    @Test
    @DisplayName("자신이 생성한 커스텀 미션 수정 성공")
    void updateCustomMission_success() {
        CustomMission originalMission = customMissionService.create("기존 미션 내용",
            MissionCategoryEnum.DAILY, testUser);
        Long missionId = originalMission.getId();
        String newContent = "수정된 미션 내용";
        MissionCategoryEnum newCategory = MissionCategoryEnum.REFRESH;

        entityManager.flush();
        entityManager.clear();

        CustomMission updatedMission = customMissionService.update(missionId, newContent,
            newCategory, testUser);

        assertThat(updatedMission).isNotNull();
        assertThat(updatedMission.getId()).isEqualTo(missionId);
        assertThat(updatedMission.getContent()).isEqualTo(newContent);
        assertThat(updatedMission.getCategory()).isEqualTo(newCategory);
        assertThat(updatedMission.getUserId()).isEqualTo(testUser.getId());

        Optional<CustomMission> foundMissionOpt = customMissionRepository.findById(missionId);
        assertThat(foundMissionOpt).isPresent();
        assertThat(foundMissionOpt.get().getContent()).isEqualTo(newContent);
        assertThat(foundMissionOpt.get().getCategory()).isEqualTo(newCategory);
    }

    @Test
    @DisplayName("다른 사용자가 생성한 커스텀 미션 수정 시 실패")
    void updateCustomMission_fail_notOwner() {
        CustomMission mission = customMissionService.create("testUser의 미션",
            MissionCategoryEnum.EMPLOYMENT, testUser);
        Long missionId = mission.getId();
        String newContent = "수정 시도";
        MissionCategoryEnum newCategory = MissionCategoryEnum.REFRESH;

        entityManager.flush();
        entityManager.clear();

        assertThatThrownBy(
            () -> customMissionService.update(missionId, newContent, newCategory, anotherUser))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("errorCode", MissionErrorCode.MISSION_ACCESS_DENIED);

        Optional<CustomMission> foundMissionOpt = customMissionRepository.findById(missionId);
        assertThat(foundMissionOpt).isPresent();
        assertThat(foundMissionOpt.get().getContent()).isEqualTo("testUser의 미션");
    }

}

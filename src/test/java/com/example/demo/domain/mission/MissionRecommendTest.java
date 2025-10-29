package com.example.demo.domain.mission;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.service.EmotionRepository;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.controller.dto.MissionCompletionCount;
import com.example.demo.mission.controller.dto.MissionResponse;
import com.example.demo.mission.regular.infrastructure.MissionScoreMinMax;
import com.example.demo.mission.regular.infrastructure.jpa.MissionCountEmbeddable;
import com.example.demo.mission.regular.infrastructure.jpa.MissionScoreEmbeddable;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionEntity;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionJpaRepository;
import com.example.demo.mission.regular.service.MissionMinMaxCache;
import com.example.demo.mission.regular.service.recommend.MissionRecommendService;
import com.example.demo.plan.service.PlanRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
public class MissionRecommendTest {

    @Autowired
    private MissionRecommendService missionRecommendService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private EmotionRepository emotionRepository;

    @MockBean
    private PlanRepository planRepository;

    @Autowired
    private RegularMissionJpaRepository regularMissionJpaRepository;

    @Autowired
    private EntityManager entityManager;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = userRepository.save(new User("test@example.com", "TestUser"));

        createTestRegularMission("DAILY 미션 1 (Sentiment 높음)", MissionCategoryEnum.DAILY, 9, 7, 7, 7,
                7, 7, 1);
        createTestRegularMission("DAILY 미션 2 (Sentiment 높음)", MissionCategoryEnum.DAILY, 5, 7, 7, 7,
                7, 7, 1);
        createTestRegularMission("DAILY 미션 4 (Sentiment 높음)", MissionCategoryEnum.DAILY, 6, 7, 7, 7,
                7, 7, 1);
        createTestRegularMission("DAILY 미션 3 (Sentiment 낮음)", MissionCategoryEnum.DAILY, 1, 7, 7, 7,
                7, 7, 1);
        createTestRegularMission("DAILY 미션 3 (Sentiment 낮음)", MissionCategoryEnum.DAILY, 1, 7, 7, 7,
                7, 7, 1);

        createTestRegularMission("REFRESH 미션 1 (Sentiment 높음)", MissionCategoryEnum.REFRESH, 9, 8,
                8, 8, 8, 8, 2);
        createTestRegularMission("REFRESH 미션 3 (Sentiment 높음)", MissionCategoryEnum.REFRESH, 2, 8,
                8, 8, 8, 8, 2);
        createTestRegularMission("REFRESH 미션 2 (Sentiment 높음)", MissionCategoryEnum.REFRESH, 9, 8,
                8, 8, 8, 8, 2);

        createTestRegularMission("EMPLOYMENT 미션 1", MissionCategoryEnum.EMPLOYMENT, 6, 6, 9, 6, 6,
                9, 3);
        createTestRegularMission("EMPLOYMENT 미션 2", MissionCategoryEnum.EMPLOYMENT, 6, 6, 8, 6, 6,
                8, 3);
        createTestRegularMission("EMPLOYMENT 미션 3", MissionCategoryEnum.EMPLOYMENT, 6, 6, 9, 6, 6,
                9, 3);
        createTestRegularMission("EMPLOYMENT 미션 4", MissionCategoryEnum.EMPLOYMENT, 6, 6, 8, 6, 6,
                8, 3);

        entityManager.flush();
        entityManager.clear();

        MissionScoreMinMax minMax = regularMissionJpaRepository.calculateMissionScoreMinMax()
                .orElseThrow(() -> new IllegalStateException("테스트 데이터 Min/Max 계산 실패"));

        MissionMinMaxCache.caching(minMax);
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
    void getRecommendedMissions_shouldReturnMissionsBasedOnEmotionAndHistory() {
        User user = testUser;

        List<MissionCompletionCount> completedMissions = List.of(
                new MissionCompletionCount(MissionCategoryEnum.DAILY, 0),
                new MissionCompletionCount(MissionCategoryEnum.REFRESH, 0),
                new MissionCompletionCount(MissionCategoryEnum.EMPLOYMENT, 10)
        );
        when(planRepository.findCompletedMissionCount(user.getId()))
                .thenReturn(completedMissions);

        Emotion emotion = new Emotion(
                user.getId(),
                0, 100, 100, 100, 100, 100,
                0, 0
        );

        when(emotionRepository.findById(user.getId()))
                .thenReturn(Optional.of(emotion));

        // when
        List<MissionResponse> responses = missionRecommendService.getRecommendedMissions(user);

        // then
        assertNotNull(responses);
        assertFalse(responses.isEmpty());

        verify(planRepository).findCompletedMissionCount(user.getId());
        verify(emotionRepository).findById(user.getId());

        System.out.println("--- 추천 미션 목록 -------------------");
        for (MissionResponse response : responses) {
            System.out.println(response);
        }
        System.out.println("------------------------------------");
    }
}
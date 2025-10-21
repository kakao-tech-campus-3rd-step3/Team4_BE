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
import com.example.demo.mission.regular.service.recommend.MissionRecommendService;
import com.example.demo.plan.service.PlanRepository;
import com.example.demo.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MissionRecommendTest {

    @MockitoBean
    private PlanRepository planRepository;

    @MockitoBean
    private EmotionRepository emotionRepository;

    @Autowired
    private MissionRecommendService recommendService;

    @Test
    void getRecommendedMissions_shouldReturnMissionsBasedOnEmotionAndHistory() {
        // given
        User user = new User("test@test.com", "수림");

        List<MissionCompletionCount> completedMissions = List.of(
                new MissionCompletionCount(MissionCategoryEnum.DAILY, 10),
                new MissionCompletionCount(MissionCategoryEnum.REFRESH, 10),
                new MissionCompletionCount(MissionCategoryEnum.EMPLOYMENT, 0)
        );
        when(planRepository.findCompletedMissionCount(user.getId()))
                .thenReturn(completedMissions);

        Emotion emotion = new Emotion(
                1L,
                0,
                100,
                100,
                100,
                100,
                100,
                0,
                0
        );
        when(emotionRepository.findById(user.getId()))
                .thenReturn(Optional.of(emotion));

        // when
        List<MissionResponse> responses = recommendService.getRecommendedMissions(user);

        // then
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        verify(planRepository).findCompletedMissionCount(user.getId());
        verify(emotionRepository).findById(user.getId());
        for (MissionResponse response : responses) {
            System.out.println(response);
        }
    }
}

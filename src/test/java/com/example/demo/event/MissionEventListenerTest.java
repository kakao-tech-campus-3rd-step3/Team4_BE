package com.example.demo.event;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import com.example.demo.domain.user.User;
import com.example.demo.domain.userEmotion.UserEmotion;
import com.example.demo.dto.plan.PlanResponse;
import com.example.demo.repository.MissionRepository;
import com.example.demo.repository.UserEmotionRepository;
import com.example.demo.service.MissionService;
import com.example.demo.service.PlanCommandService;
import com.example.demo.service.PlanQueryService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MissionEventListenerTest {

    @Autowired
    MissionService missionService;

    @Autowired
    UserEmotionRepository userEmotionRepository;

    @Autowired
    MissionRepository missionRepository;

    @Autowired
    PlanCommandService planCommandService;

    @Autowired
    PlanQueryService planQueryService;

    @Autowired
    EntityManager em;

    User user;
    Mission mission1;
    Mission mission2;

    @BeforeEach
    void setUp() {
        user = User.of("야호", "yaho@test.com");
        UserEmotion userEmotion = new UserEmotion(user, 1, 1, 1, 1, 1, 1);
        userEmotionRepository.save(userEmotion);

        //mission1, mission2 노출, 선택, 완료 횟수 모두 0
        mission1 = missionRepository.save(
            new Mission("책 읽기", MissionCategoryEnum.REFRESH, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1));

        mission2 = missionRepository.save(
            new Mission("책 10권 읽기", MissionCategoryEnum.DAILY, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1));
    }

    @Test
    void 미션_추천_시_해당_미션의_노출_횟수가_증가한다() {
        //when
        missionService.getRecommendedMissions(user);

        //then
        mission1 = missionRepository.findById(mission1.getId()).get();
        mission2 = missionRepository.findById(mission2.getId()).get();

        assertThat(mission1.getExposureCount()).isEqualTo(1);
        assertThat(mission2.getExposureCount()).isEqualTo(1);

    }

    @Test
    void 미션_선택_시_해당_미션의_선택_횟수가_증가한다() {
        //when
        planCommandService.addMissionToPlan(mission1.getId(), user);

        //then
        mission1 = missionRepository.findById(mission1.getId()).get();

        assertThat(mission1.getSelectionCount()).isEqualTo(1);

    }

    @Test
    void 미션_완료_시_해당_미션의_완료_횟수가_증가한다() {
        //given
        planCommandService.addMissionToPlan(mission1.getId(), user);
        List<PlanResponse> plans = planQueryService.getPlans(LocalDate.now(), false, user);
        PlanResponse planResponse = plans.get(0);

        //when
        planCommandService.updatePlanStatus(planResponse.getId(), true, user);

        mission1 = missionRepository.findById(mission1.getId()).get();

        //then
        assertThat(mission1.getCompletionCount()).isEqualTo(1);
    }

}

package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import com.example.demo.domain.user.User;
import com.example.demo.dto.plan.PlanResponse;
import com.example.demo.repository.MissionRepository;
import com.example.demo.repository.UserMissionRepository;
import com.example.demo.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PlanQueryServiceTest {

    @Autowired
    private PlanQueryService planQueryService;

    @Autowired
    private PlanCommandService planCommandService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserMissionRepository userMissionRepository;

    private User user1;
    private Mission mission1;
    private Mission mission2;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(User.of("사용자1", "user1@test.com"));

        mission1 = missionRepository.save(
            new Mission("책 읽기", MissionCategoryEnum.REFRESH, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
        mission2 = missionRepository.save(
            new Mission("알고리즘 문제 풀기", MissionCategoryEnum.DAILY, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    }

    @Test
    void 날짜로_일일_계획_조회_성공() {

        planCommandService.addMissionToPlan(mission1.getId(), user1);
        planCommandService.addMissionToPlan(mission2.getId(), user1);

        List<PlanResponse> plans = planQueryService.getPlans(LocalDate.now(), null, user1);

        assertThat(plans.size()).isEqualTo(2);
        assertThat(plans.get(0).getContent()).isEqualTo(mission1.getContent());
    }

    @Test
    void 완료_여부로_일일_계획_필터링_조회_성공() {

        planCommandService.addMissionToPlan(mission1.getId(), user1);
        planCommandService.addMissionToPlan(mission2.getId(), user1);

        planCommandService.updatePlanStatus(userMissionRepository.findByUser(user1).get(0).getId(),
            true, user1);

        List<PlanResponse> completedPlans = planQueryService.getPlans(LocalDate.now(), true, user1);
        List<PlanResponse> incompletedPlans = planQueryService.getPlans(LocalDate.now(), false,
            user1);

        assertThat(completedPlans.size()).isEqualTo(1);
        assertThat(completedPlans.get(0).getContent()).isEqualTo(mission1.getContent());
        assertThat(completedPlans.get(0).getIsDone()).isTrue();

        assertThat(incompletedPlans.size()).isEqualTo(1);
        assertThat(incompletedPlans.get(0).getContent()).isEqualTo(mission2.getContent());
        assertThat(incompletedPlans.get(0).getIsDone()).isFalse();
    }
}
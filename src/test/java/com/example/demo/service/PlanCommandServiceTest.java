package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import com.example.demo.domain.mission.UserMission;
import com.example.demo.domain.user.User;
import com.example.demo.repository.MissionRepository;
import com.example.demo.repository.UserMissionRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PlanCommandServiceTest {

    @Autowired
    private PlanCommandService planCommandService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserMissionRepository userMissionRepository;

    @Autowired
    private EntityManager em;

    private User user1;
    private User user2;
    private Mission mission1;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(User.of("사용자1", "user1@test.com"));
        user2 = userRepository.save(User.of("사용자2", "user2@test.com"));

        mission1 = missionRepository.save(
            new Mission("책 읽기", MissionCategoryEnum.REFRESH, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    }

    @Test
    void 미션을_일일_계획에_추가_성공() {
        planCommandService.addMissionToPlan(mission1.getId(), user1);
        em.flush();
        em.clear();

        List<UserMission> userMissions = userMissionRepository.findByUser(user1);
        assertThat(userMissions.size()).isEqualTo(1);
        assertThat(userMissions.get(0).getMission().getId()).isEqualTo(mission1.getId());
        assertThat(userMissions.get(0).getDone()).isFalse();
    }

    @Test
    void 완료하지_않은_동일_미션을_같은_날_중복_추가하면_예외_발생() {
        planCommandService.addMissionToPlan(mission1.getId(), user1);

        assertThatThrownBy(() -> planCommandService.addMissionToPlan(mission1.getId(), user1))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("오늘 아직 완료하지 않은 동일한 미션이 계획에 있습니다.");
    }

    @Test
    void 완료한_미션은_같은_날이라도_다시_추가_가능() {
        planCommandService.addMissionToPlan(mission1.getId(), user1);
        UserMission firstMission = userMissionRepository.findByUser(user1).get(0);
        planCommandService.updatePlanStatus(firstMission.getId(), true, user1);

        em.flush();
        em.clear();

        planCommandService.addMissionToPlan(mission1.getId(), user1);

        List<UserMission> userMissions = userMissionRepository.findByUser(user1);
        assertThat(userMissions.size()).isEqualTo(2);
    }

    @Test
    void 일일_계획_수행여부_체크_성공() {
        planCommandService.addMissionToPlan(mission1.getId(), user1);
        UserMission userMission = userMissionRepository.findByUser(user1).get(0);

        planCommandService.updatePlanStatus(userMission.getId(), true, user1);
        em.flush();
        em.clear();

        UserMission updatedUserMission = userMissionRepository.findById(userMission.getId()).get();
        assertThat(updatedUserMission.getDone()).isTrue();
    }

    @Test
    void 다른_사용자의_계획_수정_시도시_예외_발생() {
        planCommandService.addMissionToPlan(mission1.getId(), user1);
        UserMission userMission = userMissionRepository.findByUser(user1).get(0);

        assertThatThrownBy(
            () -> planCommandService.updatePlanStatus(userMission.getId(), true, user2))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("본인의 계획만 수정 또는 삭제할 수 있습니다.");
    }

    @Test
    void 일일_계획_삭제_성공() {
        planCommandService.addMissionToPlan(mission1.getId(), user1);
        UserMission userMission = userMissionRepository.findByUser(user1).get(0);

        planCommandService.deletePlan(userMission.getId(), user1);
        em.flush();
        em.clear();

        List<UserMission> userMissions = userMissionRepository.findByUser(user1);
        assertThat(userMissions).isEmpty();
    }

    @Test
    void 다른_사용자의_계획_삭제_시도시_예외_발생() {
        planCommandService.addMissionToPlan(mission1.getId(), user1);
        UserMission userMission = userMissionRepository.findByUser(user1).get(0);

        assertThatThrownBy(() -> planCommandService.deletePlan(userMission.getId(), user2))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("본인의 계획만 수정 또는 삭제할 수 있습니다.");
    }
}
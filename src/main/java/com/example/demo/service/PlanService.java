package com.example.demo.service;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.UserMission;
import com.example.demo.domain.user.User;
import com.example.demo.dto.plan.PlanResponse;
import com.example.demo.repository.MissionRepository;
import com.example.demo.repository.UserMissionRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {

    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;

    public void addMissionToPlan(Long missionId, User user) {
        LocalDate today = LocalDate.now();
        boolean exists = userMissionRepository.existsByUserAndMissionIdAndDoneIsFalseAndCreatedAtBetween(
            user,
            missionId,
            today.atStartOfDay(),
            today.atTime(LocalTime.MAX)
        );

        if (exists) {
            throw new RuntimeException("오늘 아직 완료하지 않은 동일한 미션이 계획에 있습니다.");
        }

        Mission mission = missionRepository.findById(missionId)
            .orElseThrow(() -> new RuntimeException("미션을 찾을 수 없습니다."));

        UserMission userMission = new UserMission(user, mission);
        userMissionRepository.save(userMission);
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getPlans(LocalDate date, Boolean isDone, User user) {
        List<UserMission> userMissions = userMissionRepository.findByUserAndDateAndDone(
            user,
            date.atStartOfDay(),
            date.atTime(LocalTime.MAX),
            isDone
        );

        return userMissions.stream()
            .map(PlanResponse::new)
            .collect(Collectors.toList());
    }

    public void updatePlanStatus(Long planId, boolean isDone, User user) {
        UserMission userMission = userMissionRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("계획을 찾을 수 없습니다."));

        if (!userMission.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 계획만 수정할 수 있습니다.");
        }

        userMission.updateDone(isDone);
    }

    public void deletePlan(Long planId, User user) {
        UserMission userMission = userMissionRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("계획을 찾을 수 없습니다."));

        if (!userMission.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 계획만 삭제할 수 있습니다.");
        }

        userMissionRepository.delete(userMission);
    }
}
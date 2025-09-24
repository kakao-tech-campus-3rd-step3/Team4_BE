package com.example.demo.service;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.UserMission;
import com.example.demo.domain.user.User;
import com.example.demo.event.mission.MissionCompletionEvent;
import com.example.demo.event.mission.MissionSelectionEvent;
import com.example.demo.repository.MissionRepository;
import com.example.demo.repository.UserMissionRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanCommandService {

    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void addMissionToPlan(Long missionId, User user) {
        validateIfMissionCanBeAdded(missionId, user);
        Mission mission = findMissionById(missionId);
        createUserMission(user, mission);
        eventPublisher.publishEvent(new MissionSelectionEvent(mission.getId()));
    }

    private void validateIfMissionCanBeAdded(Long missionId, User user) {
        LocalDate today = LocalDate.now();
        boolean isNotCompletedMissionExists = userMissionRepository.existsByUserAndMissionIdAndDoneIsFalseAndCreatedAtBetween(
            user,
            missionId,
            today.atStartOfDay(),
            today.atTime(LocalTime.MAX)
        );
        if (isNotCompletedMissionExists) {
            throw new RuntimeException("오늘 아직 완료하지 않은 동일한 미션이 계획에 있습니다.");
        }
    }

    private Mission findMissionById(Long missionId) {
        return missionRepository.findById(missionId)
            .orElseThrow(() -> new RuntimeException("미션을 찾을 수 없습니다."));
    }

    private void createUserMission(User user, Mission mission) {
        UserMission userMission = new UserMission(user, mission);
        userMissionRepository.save(userMission);
    }

    public void updatePlanStatus(Long planId, boolean isDone, User user) {
        UserMission userMission = findUserMissionById(planId);
        validateUserOwnership(userMission, user);
        userMission.updateDone(isDone);

        if (isDone) {
            eventPublisher.publishEvent(
                new MissionCompletionEvent(userMission.getMission().getId()));
        }
    }

    public void deletePlan(Long planId, User user) {
        UserMission userMission = findUserMissionById(planId);
        validateUserOwnership(userMission, user);
        userMissionRepository.delete(userMission);
    }

    private UserMission findUserMissionById(Long planId) {
        return userMissionRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("계획을 찾을 수 없습니다."));
    }

    private void validateUserOwnership(UserMission userMission, User user) {
        if (!userMission.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 계획만 수정 또는 삭제할 수 있습니다.");
        }
    }
}
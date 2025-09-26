package com.example.demo.mission.regular.service;

import com.example.demo.mission.regular.service.counter.MissionCounterService;
import com.example.demo.plan.controller.dto.PlanCreateRequest;
import com.example.demo.plan.service.PlanInternalService;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final PlanInternalService planInternalService;
    private final MissionCounterService missionCounterService;

    public void addMissionToPlan(PlanCreateRequest request, User user) {
        Long missionId = planInternalService.addMissionToPlan(request, user);
        missionCounterService.addSelectionDelta(missionId);
    }

    public void updatePlanStatus(Long planId, boolean isDone, User user) {
        Long missionId = planInternalService.updatePlanStatus(planId, isDone, user);
        if (isDone) {
            missionCounterService.addCompletionDelta(missionId);
        }
    }

}

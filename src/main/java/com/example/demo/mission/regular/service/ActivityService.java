package com.example.demo.mission.regular.service;

import com.example.demo.mission.regular.domain.MissionScore;
import com.example.demo.mission.regular.service.counter.MissionCounterService;
import com.example.demo.mission.regular.service.score.MissionScoreMinMax;
import com.example.demo.plan.controller.dto.PlanCreateRequest;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import com.example.demo.plan.service.PlanInternalService;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final PlanInternalService planInternalService;
    private final MissionCounterService missionCounterService;
    private final MissionEmotionService missionEmotionService;

    public void addMissionToPlan(PlanCreateRequest request, User user) {
        Long missionId = planInternalService.addMissionToPlan(request, user);
        missionCounterService.addSelectionDelta(missionId);
    }

    public void updatePlanStatus(Long planId, boolean isDone, User user) {
        Plan plan = planInternalService.updatePlanStatus(planId, isDone, user);
        if (isDone) {
            missionCounterService.addCompletionDelta(plan.getMissionId());
            if (plan.getMissionType() == MissionType.REGULAR) {
                missionEmotionService.mission(user, plan.getMissionId());
            }
        }
    }

}

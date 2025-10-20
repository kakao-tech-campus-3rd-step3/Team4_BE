package com.example.demo.plan.domain;

import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.PlanErrorCode;
import com.example.demo.mission.Mission;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class TodayPlans {

    private final Long userId;
    private final List<Plan> plans;

    public TodayPlans(Long userId, List<Plan> plans) {
        this.userId = userId;
        this.plans = new ArrayList<>(plans);
    }

    public void addMission(Mission mission) {
        validateMissionExist(mission);
        plans.add(mission.toPlan(userId));
    }

    public Plan updateDone(Long planId, boolean isDone) {
        Plan plan = getPlan(planId);
        plan.updateDone(isDone);
        return plan;
    }

    public void deletePlan(Long planId) {
        plans.remove(getPlan(planId));
    }

    private Plan getPlan(Long planId) {
        return plans.stream().filter(p -> p.getId().equals(planId)).findAny()
            .orElseThrow(() -> new BusinessException(PlanErrorCode.PLAN_NOT_FOUND));
    }

    private void validateMissionExist(Mission mission) {
        if (plans.stream().filter(p -> !p.isDone()).anyMatch(p -> p.hasSameMission(mission))) {
            throw new BusinessException(PlanErrorCode.MISSION_ALREADY_EXIST_IN_TODAY_PLANS);
        }
    }
}

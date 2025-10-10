package com.example.demo.plan.domain;

import com.example.demo.mission.Mission;
import java.util.List;
import lombok.Getter;

@Getter
public class TodayPlans {

    private final Long userId;
    private final List<Plan> plans;

    public TodayPlans(Long userId, List<Plan> plans) {
        this.userId = userId;
        this.plans = plans;
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
            .orElseThrow(() -> new RuntimeException("플랜이 존재하지 않습니다."));
    }

    private void validateMissionExist(Mission mission) {
        if (plans.stream().filter(p -> !p.isDone()).anyMatch(p -> p.hasSameMission(mission))) {
            throw new RuntimeException("이미 미션이 오늘의 계획에 존재합니다.");
        }
    }
}

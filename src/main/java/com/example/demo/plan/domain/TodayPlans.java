package com.example.demo.plan.domain;

import com.example.demo.mission.Mission;
import com.example.demo.user.domain.User;
import lombok.Getter;

import java.util.List;

@Getter
public class TodayPlans {

    private final User user;
    private final List<Plan> plans;

    public TodayPlans(User user, List<Plan> plans) {
        this.user = user;
        this.plans = plans;
    }

    public void addMission(Mission mission) {
        validateMissionExist(mission);
        plans.add(mission.toPlan(user));
    }

    public void updateDone(Long planId, boolean isDone) {
        Plan plan = getPlan(planId);
        plan.updateDone(isDone);
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

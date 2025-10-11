package com.example.demo.plan.controller.dto;

import com.example.demo.plan.domain.TodayPlans;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TodayPlansResponse {

    private final List<PlanResponse> plans;

    public TodayPlansResponse(TodayPlans todayPlans) {
        this.plans = todayPlans.getPlans().stream()
            .map(PlanResponse::new)
            .collect(Collectors.toList());
    }
}
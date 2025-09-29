package com.example.demo.plan.controller.dto;

import com.example.demo.plan.domain.Plan;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TodayPlansResponse {

    private final List<PlanResponse> plans;

    public TodayPlansResponse(List<Plan> plans) {
        this.plans = plans.stream()
            .map(PlanResponse::new)
            .collect(Collectors.toList());
    }

}

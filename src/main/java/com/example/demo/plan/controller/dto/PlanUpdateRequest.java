package com.example.demo.plan.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanUpdateRequest {

    @NotNull(message = "{plan.isDone.NotNull}")
    private Boolean isDone;
}
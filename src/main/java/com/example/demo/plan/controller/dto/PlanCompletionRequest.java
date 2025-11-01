package com.example.demo.plan.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanCompletionRequest {

    @NotNull(message = "{plan.isDone.NotNull}")
    private Boolean isDone;
}
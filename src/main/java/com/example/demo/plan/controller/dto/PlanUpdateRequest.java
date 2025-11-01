package com.example.demo.plan.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PlanUpdateRequest {

    @NotBlank(message = "{plan.content.NotBlank}")
    private String content;

    @NotNull(message = "{plan.category.NotNull}")
    private MissionCategoryEnum category;

}

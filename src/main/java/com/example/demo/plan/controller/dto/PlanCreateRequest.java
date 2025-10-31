package com.example.demo.plan.controller.dto;

import com.example.demo.plan.domain.MissionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanCreateRequest {

    @NotNull(message = "{plan.missionId.NotNull}")
    private Long missionId;

    @NotNull(message = "{plan.missionType.NotNull}")
    private MissionType missionType;
}

package com.example.demo.plan.controller.dto;

import com.example.demo.plan.domain.MissionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanCreateRequest {

    private Long missionId;
    private MissionType missionType;
}

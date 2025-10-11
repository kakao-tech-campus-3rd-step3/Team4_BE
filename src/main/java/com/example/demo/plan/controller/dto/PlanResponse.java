package com.example.demo.plan.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import lombok.Getter;

@Getter
public class PlanResponse {

    private final Long id;
    private final Long missionId;
    private final MissionType missionType;
    private final String content;
    private final MissionCategoryEnum category;
    private final boolean isDone;

    public PlanResponse(Plan plan) {
        this.id = plan.getId();
        this.missionId = plan.getMissionId();
        this.missionType = plan.getMissionType();
        this.content = plan.getContent();
        this.category = plan.getCategory();
        this.isDone = plan.isDone();
    }
}
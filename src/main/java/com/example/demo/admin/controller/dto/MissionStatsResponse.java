package com.example.demo.admin.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionEntity;
import lombok.Getter;

@Getter
public class MissionStatsResponse {

    private final Long id;
    private final String content;
    private final MissionCategoryEnum category;
    private final int exposureCount;
    private final int selectionCount;
    private final int completionCount;

    public MissionStatsResponse(RegularMissionEntity mission) {
        this.id = mission.getId();
        this.content = mission.getContent();
        this.category = mission.getCategory();
        this.exposureCount = mission.getMissionCountEmbeddable().getExposureCount();
        this.selectionCount = mission.getMissionCountEmbeddable().getSelectionCount();
        this.completionCount = mission.getMissionCountEmbeddable().getCompletionCount();
    }
}
package com.example.demo.mission.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import lombok.Getter;

@Getter
public class MissionResponse {

    private final Long id;
    private final String content;
    private final MissionCategoryEnum category;
    private final CustomMissionStateEnum state;

    public MissionResponse(CustomMission customMission) {
        this.id = customMission.getId();
        this.content = customMission.getContent();
        this.category = customMission.getCategory();
        this.state = customMission.getState();
    }

}

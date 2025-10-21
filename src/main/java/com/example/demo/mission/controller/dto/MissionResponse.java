package com.example.demo.mission.controller.dto;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MissionResponse {

    private final Long id;
    private final String content;
    private final MissionCategoryEnum category;

    public MissionResponse(Mission mission) {
        this.id = mission.getId();
        this.content = mission.getContent();
        this.category = mission.getCategory();
    }

}

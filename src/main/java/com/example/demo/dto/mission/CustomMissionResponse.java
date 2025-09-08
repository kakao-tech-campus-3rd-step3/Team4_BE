package com.example.demo.dto.mission;

import com.example.demo.domain.mission.CustomMission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import lombok.Getter;

@Getter
public class CustomMissionResponse {

    private Long id;
    private String content;
    private MissionCategoryEnum category;

    private CustomMissionResponse(Long id, String content, MissionCategoryEnum category) {
        this.id = id;
        this.content = content;
        this.category = category;
    }

    public static CustomMissionResponse from(CustomMission customMission) {
        return new CustomMissionResponse(
                customMission.getId(),
                customMission.getContent(),
                customMission.getCategory()
        );
    }
}

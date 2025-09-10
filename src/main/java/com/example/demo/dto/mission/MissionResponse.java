package com.example.demo.dto.mission;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import lombok.Getter;

@Getter
public class MissionResponse {

    private Long id;
    private String content;
    private MissionCategoryEnum category;

    public MissionResponse(Mission mission) {
        this.id = mission.getId();
        this.content = mission.getContent();
        this.category = mission.getCategory();
    }
}

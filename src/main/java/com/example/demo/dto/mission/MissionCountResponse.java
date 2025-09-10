package com.example.demo.dto.mission;

import com.example.demo.domain.mission.MissionCategoryEnum;
import lombok.Getter;

@Getter
public class MissionCountResponse {

    private MissionCategoryEnum category;
    private Long count;

    public MissionCountResponse(MissionCategoryEnum category, Long count) {
        this.category = category;
        this.count = count;
    }
}

package com.example.demo.dto.mission;

import com.example.demo.domain.mission.MissionCategoryEnum;
import lombok.Getter;

@Getter
public class MissionCountResponse {

    private MissionCategoryEnum category;
    private Integer count;

    public MissionCountResponse(MissionCategoryEnum category, Integer count) {
        this.category = category;
        this.count = count;
    }

    public MissionCountResponse(MissionCategoryEnum category, Long count) {
        this(category, Integer.parseInt(count.toString()));
    }
}

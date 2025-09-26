package com.example.demo.mission.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import lombok.Getter;

@Getter
public class MissionCompletionCount {

    private MissionCategoryEnum category;
    private Integer count;

    public MissionCompletionCount(MissionCategoryEnum category, Integer count) {
        this.category = category;
        this.count = count;
    }

    public MissionCompletionCount(MissionCategoryEnum category, Long count) {
        this(category, Math.toIntExact(count));
    }
}

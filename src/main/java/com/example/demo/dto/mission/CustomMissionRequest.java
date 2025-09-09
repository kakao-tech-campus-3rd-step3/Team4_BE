package com.example.demo.dto.mission;

import com.example.demo.domain.mission.MissionCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomMissionRequest {

    private String content;
    private MissionCategoryEnum category;
}

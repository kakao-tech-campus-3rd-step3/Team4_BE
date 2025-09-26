package com.example.demo.mission.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomMissionCreateRequest {

    private String content;
    private MissionCategoryEnum category;

}

package com.example.demo.mission.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMissionCreateRequest {

    private String content;
    private MissionCategoryEnum category;

}
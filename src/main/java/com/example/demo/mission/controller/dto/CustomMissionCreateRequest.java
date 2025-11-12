package com.example.demo.mission.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomMissionCreateRequest {

    @NotBlank(message = "{mission.content.NotBlank}")
    private String content;

    @NotNull(message = "{mission.category.NotNull}")
    private MissionCategoryEnum category;

}

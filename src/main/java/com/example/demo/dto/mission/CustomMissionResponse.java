package com.example.demo.dto.mission;

import com.example.demo.domain.mission.CustomMission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomMissionResponse {

    private Long id;
    private String content;
    private MissionCategoryEnum category;

    public static CustomMissionResponse from(CustomMission customMission) {
        return new CustomMissionResponse(
                customMission.getId(),
                customMission.getContent(),
                customMission.getCategory()
        );
    }
}

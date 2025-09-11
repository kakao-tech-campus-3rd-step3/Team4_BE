package com.example.demo.dto.plan;

import com.example.demo.domain.mission.CustomMission;
import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import com.example.demo.domain.mission.UserMission;
import lombok.Getter;

@Getter
public class PlanResponse {

    private final Long id;
    private final String content;
    private final MissionCategoryEnum category;
    private final Boolean isDone;

    public PlanResponse(UserMission userMission) {
        this.id = userMission.getId();
        this.isDone = userMission.getDone();

        if (userMission.getMission() != null) {
            Mission mission = userMission.getMission();
            this.content = mission.getContent();
            this.category = mission.getCategory();
        } else if (userMission.getCustomMission() != null) {
            CustomMission customMission = userMission.getCustomMission();
            this.content = customMission.getContent();
            this.category = customMission.getCategory();
        } else {
            this.content = "미션 내용 없음";
            this.category = null;
        }
    }
}
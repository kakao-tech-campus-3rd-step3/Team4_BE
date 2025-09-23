package com.example.demo.mission.custom.domain;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import com.example.demo.user.domain.User;
import lombok.Getter;

@Getter
public class CustomMission implements Mission {
    private Long id;

    private String content;
    private MissionCategoryEnum category;
    private User author;

    private CustomMissionStateEnum state;

    @Override
    public Plan toPlan(User user) {
        return new Plan(this, user);
    }

    @Override
    public MissionType getMissionType() {
        return MissionType.CUSTOM;
    }

    public CustomMission(Long id, String content, MissionCategoryEnum category, User author, CustomMissionStateEnum state) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.author = author;
        this.state = state;
    }
}

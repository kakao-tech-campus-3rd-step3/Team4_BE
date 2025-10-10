package com.example.demo.mission.regular.domain;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import lombok.Getter;

@Getter
public class RegularMission implements Mission {

    private final Long id;
    private final String content;
    private final MissionCategoryEnum category;

    public RegularMission(Long id, String content, MissionCategoryEnum category) {
        this.id = id;
        this.content = content;
        this.category = category;
    }

    @Override
    public MissionType getMissionType() {
        return MissionType.REGULAR;
    }

    @Override
    public Plan toPlan(Long userId) {
        return new Plan(this, userId);
    }
}

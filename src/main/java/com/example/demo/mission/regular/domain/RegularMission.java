package com.example.demo.mission.regular.domain;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import java.util.List;
import lombok.Getter;

@Getter
public class RegularMission implements Mission {

    private Long id;
    private String content;
    private MissionCategoryEnum category;
    private Integer missionLevel;
    private MissionScore missionScore;
    private MissionCount missionCount;
    private List<MissionTag> tags;

    public RegularMission(Long id, String content, MissionCategoryEnum category,
        Integer missionLevel, MissionScore missionScore, MissionCount missionCount,
        List<MissionTag> tags) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.missionLevel = missionLevel;
        this.missionScore = missionScore;
        this.missionCount = missionCount;
        this.tags = tags;
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
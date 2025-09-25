package com.example.demo.mission.custom.domain;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import java.util.Objects;
import lombok.Getter;

@Getter
public class CustomMission implements Mission {

    private Long id;
    private String content;
    private MissionCategoryEnum category;
    private final Long userId;
    private CustomMissionStateEnum state;

    public CustomMission(Long id, String content, MissionCategoryEnum category, Long userId,
        CustomMissionStateEnum state) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.userId = userId;
        this.state = state;
    }

    public static CustomMission create(String content, MissionCategoryEnum category,
        Long userId) {
        return new CustomMission(null, content, category, userId, CustomMissionStateEnum.WAITING);
    }

    @Override
    public Plan toPlan(Long userId) {
        return new Plan(this, userId);
    }

    @Override
    public MissionType getMissionType() {
        return MissionType.CUSTOM;
    }

    public void update(String content, MissionCategoryEnum category) {
        this.content = content;
        this.category = category;
    }

    public void validateAuthor(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw new RuntimeException("본인의 미션만 수정 또는 삭제할 수 있습니다.");
        }
    }
}
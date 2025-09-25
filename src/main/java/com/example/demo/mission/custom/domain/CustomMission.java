package com.example.demo.mission.custom.domain;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import lombok.Getter;

@Getter
public class CustomMission implements Mission {

    private Long id;

    private String content;
    private MissionCategoryEnum category;
    private final Long userId;

    private CustomMissionStateEnum state;

    @Override
    public Plan toPlan(Long userId) {
        return new Plan(this, userId);
    }

    @Override
    public MissionType getMissionType() {
        return MissionType.CUSTOM;
    }

    public CustomMission(Long id, String content, MissionCategoryEnum category, Long userId,
        CustomMissionStateEnum state) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.userId = userId;
        this.state = state;
    }

    public static CustomMission create(String content, MissionCategoryEnum category, Long userId) {
        return new CustomMission(
            null,
            content,
            category,
            userId,
            CustomMissionStateEnum.WAITING
        );
    }

    public void update(String content, MissionCategoryEnum category) {
        this.content = content;
        this.category = category;
    }

    public void validateUser(Long userId) {
        if (!this.userId.equals(userId)) {
            throw new RuntimeException("미션을 수정하거나 삭제할 권한이 없습니다.");
        }
    }

}

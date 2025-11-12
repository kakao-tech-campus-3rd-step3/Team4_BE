package com.example.demo.mission.custom.domain;

import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.MissionErrorCode;
import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import lombok.Getter;

@Getter
public class CustomMission implements Mission {

    private final Long id;

    private String content;
    private MissionCategoryEnum category;
    private final Long userId;

    @Override
    public Plan toPlan(Long userId) {
        return new Plan(this, userId);
    }

    @Override
    public MissionType getMissionType() {
        return MissionType.CUSTOM;
    }

    public CustomMission(Long id, String content, MissionCategoryEnum category, Long userId) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.userId = userId;
    }

    public CustomMission(String content, MissionCategoryEnum category, Long userId) {
        this(null, content, category, userId);
    }

    public void update(String content, MissionCategoryEnum category) {
        this.content = content;
        this.category = category;
    }

    public void validateUser(Long userId) {
        if (!this.userId.equals(userId)) {
            throw new BusinessException(MissionErrorCode.MISSION_ACCESS_DENIED);
        }
    }

}

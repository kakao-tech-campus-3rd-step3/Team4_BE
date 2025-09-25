package com.example.demo.plan.domain;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Plan {

    private Long id;
    private MissionType missionType;
    private Long userId;
    private Long missionId;
    private String content;
    private MissionCategoryEnum category;
    private boolean isDone;

    public Plan(Long id, MissionType missionType, Long userId, Long missionId, String content,
        MissionCategoryEnum category,
        boolean isDone) {
        this.id = id;
        this.missionType = missionType;
        this.userId = userId;
        this.missionId = missionId;
        this.content = content;
        this.category = category;
        this.isDone = isDone;
    }

    public Plan(Mission mission, Long userId) {
        this(null, mission.getMissionType(), userId, mission.getId(), mission.getContent(),
            mission.getCategory(), false);
    }

    public boolean hasSameMission(Mission mission) {
        if (mission == null) {
            return false;
        }
        return this.missionId != null && this.missionId.equals(mission.getId())
            && this.missionType == mission.getMissionType();
    }

    public void updateDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void validateUser(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw new RuntimeException("본인의 계획만 수정 또는 삭제할 수 있습니다.");
        }
    }
}
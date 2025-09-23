package com.example.demo.plan.domain;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.user.domain.User;
import lombok.Getter;

@Getter
public class Plan {

    private Long id;
    private MissionType missionType;
    private User user;
    private Long missionId;
    private String content;
    private MissionCategoryEnum category;
    private boolean isDone;

    public Plan(Long id, MissionType missionType, User user, Long missionId, String content, MissionCategoryEnum category,
        boolean isDone) {
        this.id = id;
        this.missionType = missionType;
        this.user = user;
        this.missionId = missionId;
        this.content = content;
        this.category = category;
        this.isDone = isDone;
    }

    public Plan(Mission mission, User user) {
        this(null, mission.getMissionType(), user, mission.getId(), mission.getContent(), mission.getCategory(), false);
    }

    public boolean hasSameMission(Mission mission) {
        if (mission == null) {
            return false;
        }
        return this.missionId != null && this.missionId.equals(mission.getId()) && this.missionType == mission.getMissionType();
    }

    public void updateDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void validateUser(User user) {
        if (!this.user.equals(user)) {
            throw new RuntimeException("본인의 계획만 수정 또는 삭제할 수 있습니다.");
        }
    }
}

package com.example.demo.event;

import lombok.Getter;

@Getter
public class MissionCountEvent {

    private final Long missionId;
    private final MissionCountType type;

    public MissionCountEvent(Long missionId, MissionCountType type) {
        this.missionId = missionId;
        this.type = type;
    }

    public enum MissionCountType {
        EXPOSURE,
        SELECTION,
        COMPLETION
    }
}

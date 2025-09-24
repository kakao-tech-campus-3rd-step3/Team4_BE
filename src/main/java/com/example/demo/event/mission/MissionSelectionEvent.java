package com.example.demo.event.mission;

import lombok.Getter;

@Getter
public class MissionSelectionEvent {

    private final Long missionId;

    public MissionSelectionEvent(Long missionId) {
        this.missionId = missionId;
    }
}

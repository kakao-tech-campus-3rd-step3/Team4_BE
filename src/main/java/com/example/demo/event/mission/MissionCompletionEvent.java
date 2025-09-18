package com.example.demo.event.mission;

import lombok.Getter;

@Getter
public class MissionCompletionEvent {

    private final Long missionId;

    public MissionCompletionEvent(Long missionId) {
        this.missionId = missionId;
    }
}

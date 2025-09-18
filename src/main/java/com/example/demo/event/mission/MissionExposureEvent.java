package com.example.demo.event.mission;

import java.util.List;
import lombok.Getter;

@Getter
public class MissionExposureEvent {

    private final List<Long> missionIds;

    public MissionExposureEvent(List<Long> missionIds) {
        this.missionIds = missionIds;
    }
}

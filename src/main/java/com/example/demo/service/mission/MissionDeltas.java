package com.example.demo.service.mission;

import com.example.demo.service.mission.MissionDelta.CounterType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

@Getter
public class MissionDeltas {

    private final Map<Long, MissionDelta> delta = new ConcurrentHashMap<>();

    public void increment(Long missionId, CounterType counterType) {
        getMissionDelta(missionId).increment(counterType);
    }

    public void increment(List<Long> missionIds, CounterType counterType) {
        for (Long missionId : missionIds) {
            getMissionDelta(missionId).increment(counterType);
        }
    }

    private MissionDelta getMissionDelta(Long missionId) {
        return delta.computeIfAbsent(missionId, k -> new MissionDelta());
    }

    public void clear() {
        delta.clear();
    }
}

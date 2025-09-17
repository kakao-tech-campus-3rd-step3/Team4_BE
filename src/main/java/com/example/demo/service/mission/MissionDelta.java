package com.example.demo.service.mission;

import java.util.EnumMap;

public class MissionDelta {

    private final EnumMap<CounterType, Integer> counts = new EnumMap<>(CounterType.class);

    public MissionDelta() {
        for (CounterType value : CounterType.values()) {
            counts.put(value, 0);
        }
    }

    public void increment(CounterType type) {
        counts.merge(type, 1, Integer::sum);
    }


    public Integer getCount(CounterType type) {
        return counts.get(type);
    }

    public enum CounterType {
        EXPOSURE,
        SELECTION,
        COMPLETION
    }
}

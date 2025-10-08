package com.example.demo.mission.regular.service.score;

import com.example.demo.emotion.domain.EmotionType;
import java.util.EnumMap;
import java.util.Map;

public class MissionNormalization {

    private final Map<EmotionType, Integer> normalizedScores;

    public MissionNormalization(Map<EmotionType, Integer> normalizedScores) {
        this.normalizedScores = new EnumMap<>(normalizedScores);
    }

    public Integer get(EmotionType type) {
        return normalizedScores.get(type);
    }
}

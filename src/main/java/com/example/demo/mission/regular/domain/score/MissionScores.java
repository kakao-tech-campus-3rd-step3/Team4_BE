package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.regular.service.score.MissionNormalization;
import java.util.EnumMap;
import java.util.Map;

public class MissionScores {

    private final Map<EmotionType, MissionScore> scores = new EnumMap<>(EmotionType.class);

    public MissionScores(Integer sentimentScore, Integer energyScore, Integer cognitiveScore,
        Integer relationshipScore, Integer stressScore, Integer employmentScore) {
        scores.put(EmotionType.SENTIMENT, new MissionScore(sentimentScore));
        scores.put(EmotionType.ENERGY, new MissionScore(energyScore));
        scores.put(EmotionType.COGNITIVE, new MissionScore(cognitiveScore));
        scores.put(EmotionType.RELATIONSHIP, new MissionScore(relationshipScore));
        scores.put(EmotionType.STRESS, new MissionScore(stressScore));
        scores.put(EmotionType.EMPLOYMENT, new MissionScore(employmentScore));
    }

    public MissionNormalization normalize(Map<EmotionType, MinMaxValue> minMaxData) {
        Map<EmotionType, Integer> normalized = new EnumMap<>(EmotionType.class);

        for (EmotionType type : EmotionType.values()) {
            MissionScore score = scores.get(type);
            MinMaxValue minMax = minMaxData.get(type);
            if (score != null && minMax != null) {
                normalized.put(type, score.normalize(minMax.getMin(), minMax.getMax()));
            }
        }

        return new MissionNormalization(normalized);
    }


}

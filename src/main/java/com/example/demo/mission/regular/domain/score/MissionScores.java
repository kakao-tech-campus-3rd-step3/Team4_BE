package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.regular.service.score.MissionNormalization;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MissionScores {

    private final List<MissionScore> scores = new ArrayList<>();

    public MissionScores(Integer sentimentScore, Integer energyScore, Integer cognitiveScore,
        Integer relationshipScore, Integer stressScore, Integer employmentScore) {
        scores.add(new SentimentScore(sentimentScore));
        scores.add(new EnergyScore(energyScore));
        scores.add(new CognitiveScore(cognitiveScore));
        scores.add(new RelationshipScore(relationshipScore));
        scores.add(new StressScore(stressScore));
        scores.add(new EmploymentScore(employmentScore));
    }

    public MissionNormalization normalize(Map<EmotionType, MinMaxValue> minMaxData) {
        Map<EmotionType, Integer> normalized = new EnumMap<>(EmotionType.class);

        for (MissionScore score : scores) {
            EmotionType type = score.getEmotionType();
            MinMaxValue minMax = minMaxData.get(type);
            if (minMax != null) {
                normalized.put(type, score.normalize(minMax.getMin(), minMax.getMax()));
            }
        }

        return new MissionNormalization(
            normalized.get(EmotionType.SENTIMENT),
            normalized.get(EmotionType.ENERGY),
            normalized.get(EmotionType.COGNITIVE),
            normalized.get(EmotionType.RELATIONSHIP),
            normalized.get(EmotionType.STRESS),
            normalized.get(EmotionType.EMPLOYMENT)
        );
    }


}

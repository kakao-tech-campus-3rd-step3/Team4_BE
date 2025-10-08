package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.regular.service.score.MissionNormalization;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MissionScores {

    private final List<MissionScore> scores = new ArrayList<>();

    public MissionScores(Integer sentimentScore, Integer energyScore, Integer cognitiveScore,
        Integer relationshipScore, Integer stressScore, Integer employmentScore) {
        scores.add(new SentimentScore(sentimentScore));
        scores.add(new MissionScore(energyScore, EmotionType.ENERGY));
        scores.add(new MissionScore(cognitiveScore, EmotionType.COGNITIVE));
        scores.add(new MissionScore(relationshipScore, EmotionType.RELATIONSHIP));
        scores.add(new MissionScore(stressScore, EmotionType.STRESS));
        scores.add(new MissionScore(employmentScore, EmotionType.EMPLOYMENT));
    }

    public MissionNormalization normalize() {

    }
}

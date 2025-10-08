package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;

public class CognitiveScore extends MissionScore {

    public CognitiveScore(Integer score) {
        super(score);
    }

    @Override
    public EmotionType getEmotionType() {
        return EmotionType.COGNITIVE;
    }
}

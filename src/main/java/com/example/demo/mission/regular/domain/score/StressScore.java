package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;

public class StressScore extends MissionScore {

    public StressScore(Integer score) {
        super(score);
    }

    @Override
    public EmotionType getEmotionType() {
        return EmotionType.STRESS;
    }
}

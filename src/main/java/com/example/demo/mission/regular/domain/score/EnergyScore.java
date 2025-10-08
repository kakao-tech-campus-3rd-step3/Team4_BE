package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;

public class EnergyScore extends MissionScore {

    public EnergyScore(Integer score) {
        super(score);
    }

    @Override
    public EmotionType getEmotionType() {
        return EmotionType.ENERGY;
    }
}

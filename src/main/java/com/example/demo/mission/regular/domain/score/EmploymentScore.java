package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;

public class EmploymentScore extends MissionScore {

    public EmploymentScore(Integer score) {
        super(score);
    }

    @Override
    public EmotionType getEmotionType() {
        return EmotionType.EMPLOYMENT;
    }
}

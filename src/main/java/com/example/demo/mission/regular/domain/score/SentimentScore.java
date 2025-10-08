package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;

public class SentimentScore implements MissionScore {

    private Integer score;

    public SentimentScore(Integer score) {
        this.score = score;
    }

    @Override
    public Integer normalize(Integer min, Integer max) {
        return (int) Math.floor(((double) (score - min) / (max - min)) * 10.0);
    }

    @Override
    public EmotionType getEmotionType() {
        return EmotionType.SENTIMENT;
    }
}

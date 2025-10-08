package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;
import lombok.Getter;

@Getter
public abstract class MissionScore {

    private Integer score;

    public Integer normalize(Integer min, Integer max) {
        return (int) Math.floor(((double) (score - min) / (max - min)) * 10.0);
    }

    public abstract EmotionType getEmotionType();

    public MissionScore(Integer score) {
        this.score = score;
    }

}

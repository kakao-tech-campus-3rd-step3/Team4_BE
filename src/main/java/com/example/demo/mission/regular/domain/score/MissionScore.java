package com.example.demo.mission.regular.domain.score;

import lombok.Getter;

@Getter
public class MissionScore {

    private Integer score;

    public MissionScore(Integer score) {
        this.score = score;
    }

    public Integer normalize(Integer min, Integer max) {
        return (int) Math.floor(((double) (score - min) / (max - min)) * 10.0);
    }

}

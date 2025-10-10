package com.example.demo.mission.regular.domain.score;

import lombok.Getter;

@Getter
public class MinMaxValue {

    private final Integer min;
    private final Integer max;

    public MinMaxValue(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }
}

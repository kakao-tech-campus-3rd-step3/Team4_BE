package com.example.demo.mission.regular.service.score;

import lombok.Getter;

@Getter
public class MinMaxValue {

    private Integer min;
    private Integer max;

    public MinMaxValue(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

}

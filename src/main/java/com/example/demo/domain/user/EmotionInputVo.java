package com.example.demo.domain.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// EmotionTestResultDto -> convertor -> EmotionInputVo
public class EmotionInputVo {
    private final Map<EmotionType, Integer> initValues = new HashMap<>();

    public Set<EmotionType> keySet() {
        return initValues.keySet();
    }

    public Integer get(EmotionType type) {
        return initValues.get(type);
    }

    public void put(EmotionType type, Integer value) {
        initValues.put(type, value);
    }
}

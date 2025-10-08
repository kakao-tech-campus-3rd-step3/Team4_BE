package com.example.demo.mission.regular.domain.score;

import com.example.demo.emotion.domain.EmotionType;

public interface MissionScore {

    Integer normalize(Integer min, Integer max);

    EmotionType getEmotionType();

}

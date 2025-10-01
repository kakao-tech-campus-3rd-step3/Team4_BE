package com.example.demo.mission.regular.service.score;

import com.example.demo.emotion.domain.EmotionType;
import java.util.Map;

public class MissionScoreMinMax {
    private static Map<EmotionType, Integer> minimum;
    private static Map<EmotionType, Integer> maximum;

    public static Integer getMin(EmotionType emotionType) {
        return minimum.get(emotionType);
    }

    public static Integer getMax(EmotionType emotionType) {
        return maximum.get(emotionType);
    }
}

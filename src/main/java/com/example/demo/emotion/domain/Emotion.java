package com.example.demo.emotion.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class Emotion {

    private static final double SHORT = 0.4D;
    private static final double LONG = 0.1D;

    private static final double HIGH_THRESHOLD = 8.0D;
    private static final double MID_THRESHOLD = 5.0D;
    private static final double CHANGE_RATE_THRESHOLD = 0.3D;

    private Long userId;
    private Map<EmotionType, Integer> emotions = new HashMap<>();

    private double avgDangerLevel;
    private double recentDangerLevel;

    public Emotion(Long userId) {
        this.userId = userId;
        emotions.put(EmotionType.SENTIMENT, 0);
        emotions.put(EmotionType.ENERGY, 0);
        emotions.put(EmotionType.COGNITIVE, 0);
        emotions.put(EmotionType.RELATIONSHIP, 0);
        emotions.put(EmotionType.STRESS, 0);
        emotions.put(EmotionType.EMPLOYMENT, 0);
        avgDangerLevel = 10;
        recentDangerLevel = 10;
    }

    public Emotion(Long userId, Integer sentimentLevel, Integer energyLevel, Integer cognitiveLevel,
        Integer relationshipLevel, Integer stressLevel, Integer employmentLevel, double avgDangerLevel, double recentDangerLevel) {
        this.userId = userId;
        emotions.put(EmotionType.SENTIMENT, sentimentLevel);
        emotions.put(EmotionType.ENERGY, energyLevel);
        emotions.put(EmotionType.COGNITIVE, cognitiveLevel);
        emotions.put(EmotionType.RELATIONSHIP, relationshipLevel);
        emotions.put(EmotionType.STRESS, stressLevel);
        emotions.put(EmotionType.EMPLOYMENT, employmentLevel);
        this.avgDangerLevel = avgDangerLevel;
        this.recentDangerLevel = recentDangerLevel;
    }

    public void adjust(EmotionType type, int delta) {
        emotions.put(type, emotions.get(type) + delta);
    }

    public void applyDangerLevel(int dangerLevel) {
        avgDangerLevel = LONG * dangerLevel + (1 - LONG) * avgDangerLevel;
        recentDangerLevel = SHORT * dangerLevel + (1 - SHORT) * recentDangerLevel;
    }

    public DangerState getDangerState() {
        double changeRate = (recentDangerLevel - avgDangerLevel) / avgDangerLevel;
        if (recentDangerLevel > HIGH_THRESHOLD) {
            return DangerState.HIGH_DANGER;
        }
        else if (recentDangerLevel > MID_THRESHOLD && changeRate > CHANGE_RATE_THRESHOLD) {
            return DangerState.BURST;
        }
        else if (avgDangerLevel > MID_THRESHOLD) {
            // avgDangerLevel의 지속적 증가 검사로 개선의 여지가 있음
            return DangerState.CHRONIC;
        }
        else {
            return DangerState.STABLE;
        }
    }

    public EmotionType getMinEmotion() {
        return emotions.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Integer getLevel(EmotionType emotionType) {
        return emotions.get(emotionType);
    }

}

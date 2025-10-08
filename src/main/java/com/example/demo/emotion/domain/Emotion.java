package com.example.demo.emotion.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class Emotion {

    private Long userId;
    private Map<EmotionType, Integer> emotions = new HashMap<>();

    public Emotion(Long userId) {
        this.userId = userId;
        emotions.put(EmotionType.SENTIMENT, 0);
        emotions.put(EmotionType.ENERGY, 0);
        emotions.put(EmotionType.COGNITIVE, 0);
        emotions.put(EmotionType.RELATIONSHIP, 0);
        emotions.put(EmotionType.STRESS, 0);
        emotions.put(EmotionType.EMPLOYMENT, 0);
    }

    public Emotion(Long userId, Integer sentimentLevel, Integer energyLevel, Integer cognitiveLevel,
        Integer relationshipLevel, Integer stressLevel, Integer employmentLevel) {
        this.userId = userId;
        emotions.put(EmotionType.SENTIMENT, sentimentLevel);
        emotions.put(EmotionType.ENERGY, energyLevel);
        emotions.put(EmotionType.COGNITIVE, cognitiveLevel);
        emotions.put(EmotionType.RELATIONSHIP, relationshipLevel);
        emotions.put(EmotionType.STRESS, stressLevel);
        emotions.put(EmotionType.EMPLOYMENT, employmentLevel);
    }

    public void adjust(EmotionType type, int delta) {
        emotions.put(type, emotions.get(type) + delta);
    }

    public EmotionType getMinEmotion() {
        return emotions.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    public Integer getSentimentLevel() {
        return emotions.get(EmotionType.SENTIMENT);
    }

    public Integer getEnergyLevel() {
        return emotions.get(EmotionType.ENERGY);
    }

    public Integer getCognitiveLevel() {
        return emotions.get(EmotionType.COGNITIVE);
    }

    public Integer getRelationShipLevel() {
        return emotions.get(EmotionType.RELATIONSHIP);
    }

    public Integer getStressLevel() {
        return emotions.get(EmotionType.STRESS);
    }

    public Integer getEmploymentLevel() {
        return emotions.get(EmotionType.EMPLOYMENT);
    }
}

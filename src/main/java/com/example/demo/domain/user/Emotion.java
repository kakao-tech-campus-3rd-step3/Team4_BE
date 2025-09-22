package com.example.demo.domain.user;

import jakarta.persistence.Embeddable;
import java.util.HashMap;
import java.util.Map;

@Embeddable
public class Emotion {

    private Integer sentimentLevel = 0;
    private Integer energyLevel = 0;
    private Integer cognitiveLevel = 0;
    private Integer relationshipLevel = 0;
    private Integer stressLevel = 0;
    private Integer employmentLevel = 0;

    private boolean isInitialized = false;

    protected Emotion() {}

    public Emotion(EmotionInputVo result) {
        for (EmotionType type : result.keySet()) {
            switch (type) {
                case SENTIMENT -> sentimentLevel = result.get(type);
                case ENERGY -> energyLevel = result.get(type);
                case COGNITIVE -> cognitiveLevel = result.get(type);
                case RELATIONSHIP -> relationshipLevel = result.get(type);
                case STRESS -> stressLevel = result.get(type);
                case EMPLOYMENT -> employmentLevel = result.get(type);
            }
        }
        isInitialized = true;
    }

    public void adjustSentiment(int delta) {
        sentimentLevel += delta;
    }

    public void adjustEnergy(int delta) {
        energyLevel += delta;
    }

    public void adjustCognitive(int delta) {
        cognitiveLevel += delta;
    }

    public void adjustRelationship(int delta) {
        relationshipLevel += delta;
    }

    public void adjustStress(int delta) {
        stressLevel += delta;
    }

    public void adjustEmployment(int delta) {
        employmentLevel += delta;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public EmotionType getMinEmotion() {
        Map<EmotionType, Integer> map = new HashMap<>();
        map.put(EmotionType.SENTIMENT, sentimentLevel);
        map.put(EmotionType.ENERGY, energyLevel);
        map.put(EmotionType.COGNITIVE, cognitiveLevel);
        map.put(EmotionType.RELATIONSHIP, relationshipLevel);
        map.put(EmotionType.STRESS, stressLevel);

        return map.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
}

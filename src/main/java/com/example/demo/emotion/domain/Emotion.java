package com.example.demo.emotion.domain;

import com.example.demo.mission.regular.service.score.MissionNormalization;
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

    public void updateAllUserEmotionScores(MissionNormalization missionNormalization) {
        for (EmotionType emotionType : EmotionType.values()) {
            this.updateUserEmotionScore(missionNormalization.get(emotionType), emotionType);
        }
    }

    private void updateUserEmotionScore(Integer normalizedScore, EmotionType emotionType) {
        Integer currentEmotionValue = emotions.get(emotionType);
        double a = 0.3;   // 최소 반영 비율 (30%)
        double b = 0.7;   // 감쇠 비율
        double k = 100.0; // 감쇠 강도

        // 점수가 높을수록 반영률 감소
        double factor = a + b / Math.sqrt(1 + (double) currentEmotionValue / k);
        int delta = (int) Math.round(normalizedScore * factor);

        // score가 0 이상이 되도록 하한 적용
        if (currentEmotionValue + delta < 0) {
            delta = -currentEmotionValue;  // 최소 0 보장
        }

        adjust(emotionType, delta);
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

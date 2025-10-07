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
        this.updateUserEmotionScore(missionNormalization.getSentimentNormalization(),
                EmotionType.SENTIMENT);
        this.updateUserEmotionScore(missionNormalization.getEnergyNormalization(),
                EmotionType.ENERGY);
        this.updateUserEmotionScore(missionNormalization.getCognitiveNormalization(),
                EmotionType.COGNITIVE);
        this.updateUserEmotionScore(missionNormalization.getRelationshipNormalization(),
                EmotionType.RELATIONSHIP);
        this.updateUserEmotionScore(missionNormalization.getStressNormalization(),
                EmotionType.STRESS);
        this.updateUserEmotionScore(missionNormalization.getEmploymentNormalization(),
                EmotionType.EMPLOYMENT);
    }

    private void updateUserEmotionScore(Integer normalizedScore, EmotionType emotionType) {
        // 보통 α는 0과 1 사이의 값(예: 0.01, 0.1, 0.5)으로 시작합니다.
        // 감정 수치 U가 무한대로 커지기 때문에, α는 작게 설정하는 것이 일반적입니다.
        double alpha = 0.5;
        // 상한선없는 둔화형 누적
        double currentEmotionValue = emotions.get(emotionType).doubleValue();
        double delta = alpha * normalizedScore * (1.0 / Math.sqrt(1.0 + currentEmotionValue));
        Integer emotionIncrease = (int) Math.ceil(delta);
        emotions.put(emotionType, emotions.get(emotionType) + emotionIncrease);
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

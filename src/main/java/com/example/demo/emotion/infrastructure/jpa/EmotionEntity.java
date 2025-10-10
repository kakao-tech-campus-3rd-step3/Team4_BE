package com.example.demo.emotion.infrastructure.jpa;

import com.example.demo.emotion.domain.Emotion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_emotion")
public class EmotionEntity {

    @Id
    private Long userId;

    @Column(nullable = false)
    private Integer sentimentLevel;

    @Column(nullable = false)
    private Integer energyLevel;

    @Column(nullable = false)
    private Integer cognitiveLevel;

    @Column(nullable = false)
    private Integer relationshipLevel;

    @Column(nullable = false)
    private Integer stressLevel;

    @Column(nullable = false)
    private Integer employmentLevel;

    @Column(nullable = false)
    private Double avgDangerLevel;

    @Column(nullable = false)
    private Double recentDangerLevel;

    protected EmotionEntity() {}

    public EmotionEntity(Long userId, Integer sentimentLevel, Integer energyLevel, Integer cognitiveLevel,
        Integer relationshipLevel, Integer stressLevel, Integer employmentLevel, Double avgDangerLevel, Double recentDangerLevel) {
        this.userId = userId;
        this.sentimentLevel = sentimentLevel;
        this.energyLevel = energyLevel;
        this.cognitiveLevel = cognitiveLevel;
        this.relationshipLevel = relationshipLevel;
        this.stressLevel = stressLevel;
        this.employmentLevel = employmentLevel;
        this.avgDangerLevel = avgDangerLevel;
        this.recentDangerLevel = recentDangerLevel;
    }

    public static EmotionEntity fromModel(Emotion model) {
        return new EmotionEntity(
            model.getUserId(),
            model.getSentimentLevel(),
            model.getEnergyLevel(),
            model.getCognitiveLevel(),
            model.getRelationShipLevel(),
            model.getStressLevel(),
            model.getEmploymentLevel(),
            model.getAvgDangerLevel(),
            model.getRecentDangerLevel()
        );
    }

    public Emotion toModel() {
        return new Emotion(userId, sentimentLevel, employmentLevel, cognitiveLevel, relationshipLevel, stressLevel, employmentLevel, avgDangerLevel, recentDangerLevel);
    }

    public void updateFromModel(Emotion model) {
        this.sentimentLevel = model.getSentimentLevel();
        this.energyLevel = model.getEnergyLevel();
        this.cognitiveLevel = model.getCognitiveLevel();
        this.relationshipLevel = model.getRelationShipLevel();
        this.stressLevel = model.getStressLevel();
        this.employmentLevel = model.getEmploymentLevel();
    }
}

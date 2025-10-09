package com.example.demo.emotion.infrastructure.jpa;

import static com.example.demo.emotion.domain.EmotionType.COGNITIVE;
import static com.example.demo.emotion.domain.EmotionType.EMPLOYMENT;
import static com.example.demo.emotion.domain.EmotionType.ENERGY;
import static com.example.demo.emotion.domain.EmotionType.RELATIONSHIP;
import static com.example.demo.emotion.domain.EmotionType.SENTIMENT;
import static com.example.demo.emotion.domain.EmotionType.STRESS;

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

    protected EmotionEntity() {}

    public EmotionEntity(Long userId, Integer sentimentLevel, Integer energyLevel, Integer cognitiveLevel,
        Integer relationshipLevel, Integer stressLevel, Integer employmentLevel) {
        this.userId = userId;
        this.sentimentLevel = sentimentLevel;
        this.energyLevel = energyLevel;
        this.cognitiveLevel = cognitiveLevel;
        this.relationshipLevel = relationshipLevel;
        this.stressLevel = stressLevel;
        this.employmentLevel = employmentLevel;
    }

    public static EmotionEntity fromModel(Emotion model) {
        return new EmotionEntity(
            model.getUserId(),
            model.getLevel(SENTIMENT),
            model.getLevel(ENERGY),
            model.getLevel(COGNITIVE),
            model.getLevel(RELATIONSHIP),
            model.getLevel(STRESS),
            model.getLevel(EMPLOYMENT)
        );
    }

    public Emotion toModel() {
        return new Emotion(userId, sentimentLevel, employmentLevel, cognitiveLevel, relationshipLevel, stressLevel, employmentLevel);
    }

    public void updateFromModel(Emotion model) {
        this.sentimentLevel = model.getLevel(SENTIMENT);
        this.energyLevel = model.getLevel(ENERGY);
        this.cognitiveLevel = model.getLevel(COGNITIVE);
        this.relationshipLevel = model.getLevel(RELATIONSHIP);
        this.stressLevel = model.getLevel(STRESS);
        this.employmentLevel = model.getLevel(EMPLOYMENT);
    }
}

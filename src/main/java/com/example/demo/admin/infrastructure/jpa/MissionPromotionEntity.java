package com.example.demo.admin.infrastructure.jpa;

import com.example.demo.admin.domain.MissionPromotionScore;
import com.example.demo.admin.domain.MissionPromotion;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionScore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class MissionPromotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionCategoryEnum category;

    @Embedded
    private CustomMissionScore missionScore;

    private Integer level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomMissionStateEnum state;

    protected MissionPromotionEntity() {
    }

    private MissionPromotionEntity(Long id, String content, MissionCategoryEnum category,
            CustomMissionScore missionScore, Integer level, CustomMissionStateEnum state) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.missionScore = missionScore;
        this.level = level;
        this.state = state;
    }

    public static MissionPromotionEntity fromModel(MissionPromotion missionPromotion) {
        MissionPromotionScore score = missionPromotion.getScore();
        CustomMissionScore customMissionScore = null;
        if (score != null) {
            customMissionScore = new CustomMissionScore(score.getSentimentScore(),
                    score.getEnergyScore(),
                    score.getCognitiveScore(), score.getRelationshipScore(),
                    score.getStressScore(), score.getEmploymentScore());
        }
        return new MissionPromotionEntity(
                missionPromotion.getId(),
                missionPromotion.getContent(),
                missionPromotion.getCategory(),
                customMissionScore,
                missionPromotion.getLevel(),
                missionPromotion.getState()
        );
    }

    public MissionPromotion toModel() {
        MissionPromotionScore promotionScore = null;
        if (missionScore != null) {
            promotionScore = new MissionPromotionScore(
                    missionScore.getSentimentScore(),
                    missionScore.getEnergyScore(),
                    missionScore.getCognitiveScore(),
                    missionScore.getRelationshipScore(),
                    missionScore.getStressScore(),
                    missionScore.getEmploymentScore());
        }
        return new MissionPromotion(id, content, category, level, promotionScore, state);
    }
}

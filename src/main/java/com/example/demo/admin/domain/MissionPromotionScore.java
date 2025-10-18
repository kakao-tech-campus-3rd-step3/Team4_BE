package com.example.demo.admin.domain;

import lombok.Getter;

@Getter
public class MissionPromotionScore {

    private Integer sentimentScore;
    private Integer energyScore;
    private Integer cognitiveScore;
    private Integer relationshipScore;
    private Integer stressScore;
    private Integer employmentScore;

    public MissionPromotionScore(Integer sentimentScore, Integer energyScore,
            Integer cognitiveScore, Integer relationshipScore, Integer stressScore,
            Integer employmentScore) {
        this.sentimentScore = sentimentScore;
        this.energyScore = energyScore;
        this.cognitiveScore = cognitiveScore;
        this.relationshipScore = relationshipScore;
        this.stressScore = stressScore;
        this.employmentScore = employmentScore;
    }
}

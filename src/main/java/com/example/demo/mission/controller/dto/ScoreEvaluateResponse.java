package com.example.demo.mission.controller.dto;

import lombok.Getter;

@Getter
public class ScoreEvaluateResponse {
    private final Integer sentimentScore;
    private final Integer energyScore;
    private final Integer cognitiveScore;
    private final Integer relationshipScore;
    private final Integer stressScore;
    private final Integer employmentScore;
    private final Integer level;

    public ScoreEvaluateResponse(Integer sentimentScore, Integer energyScore,
        Integer cognitiveScore,
        Integer relationshipScore, Integer stressScore, Integer employmentScore, Integer level) {
        this.sentimentScore = sentimentScore;
        this.energyScore = energyScore;
        this.cognitiveScore = cognitiveScore;
        this.relationshipScore = relationshipScore;
        this.stressScore = stressScore;
        this.employmentScore = employmentScore;
        this.level = level;
    }
}

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
        Integer cognitiveScore, Integer relationshipScore,
        Integer stressScore, Integer employmentScore, Integer level) {
        this.sentimentScore = clamp(sentimentScore);
        this.energyScore = clamp(energyScore);
        this.cognitiveScore = clamp(cognitiveScore);
        this.relationshipScore = clamp(relationshipScore);
        this.stressScore = clamp(stressScore);
        this.employmentScore = clamp(employmentScore);
        this.level = clamp(level);
    }

    private Integer clamp(Integer value) {
        if (value == null) return 0;
        return Math.max(0, Math.min(10, value));
    }
}

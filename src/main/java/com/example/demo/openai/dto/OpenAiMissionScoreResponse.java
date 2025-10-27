package com.example.demo.openai.dto;

import lombok.Getter;

@Getter
public class OpenAiMissionScoreResponse {

    private Integer sentimentScore;
    private Integer energyScore;
    private Integer cognitiveScore;
    private Integer relationshipScore;
    private Integer stressScore;
    private Integer employmentScore;
    private Integer level;

    public OpenAiMissionScoreResponse() {
    }
}

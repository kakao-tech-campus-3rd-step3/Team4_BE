package com.example.demo.common.infrastructure.openai.dto;

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

    public static OpenAiMissionScoreResponse zero() {
        OpenAiMissionScoreResponse response = new OpenAiMissionScoreResponse();
        response.sentimentScore = 0;
        response.energyScore = 0;
        response.cognitiveScore = 0;
        response.relationshipScore = 0;
        response.stressScore = 0;
        response.employmentScore = 0;
        response.level = 0;
        return response;
    }
}

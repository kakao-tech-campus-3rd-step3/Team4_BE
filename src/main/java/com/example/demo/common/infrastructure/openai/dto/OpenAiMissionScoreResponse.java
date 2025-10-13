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

}

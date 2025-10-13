package com.example.demo.mission.custom.service;

import com.example.demo.common.infrastructure.openai.OpenAiClient;
import com.example.demo.common.infrastructure.openai.dto.OpenAiMissionScoreResponse;
import com.example.demo.mission.controller.dto.ScoreEvaluateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreEvaluationService {

    private final OpenAiClient openAiClient;

    public ScoreEvaluateResponse evaluateScore(String content) {
        OpenAiMissionScoreResponse response = openAiClient.getScore(content);
        return new ScoreEvaluateResponse(
            response.getSentimentScore(),
            response.getEnergyScore(),
            response.getCognitiveScore(),
            response.getRelationshipScore(),
            response.getStressScore(),
            response.getEmploymentScore(),
            response.getLevel()
        );
    }

}

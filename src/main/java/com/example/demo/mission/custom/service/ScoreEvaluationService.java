package com.example.demo.mission.custom.service;

import com.example.demo.openai.OpenAiClient;
import com.example.demo.openai.dto.OpenAiMissionScoreResponse;
import com.example.demo.mission.controller.dto.ScoreEvaluateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreEvaluationService {

    private final OpenAiClient openAiClient;

    public ScoreEvaluateResponse evaluateScore(String content) {
        OpenAiMissionScoreResponse score = openAiClient.getScore(content);
        return new ScoreEvaluateResponse(
            score.getSentimentScore(),
            score.getEnergyScore(),
            score.getCognitiveScore(),
            score.getRelationshipScore(),
            score.getStressScore(),
            score.getEmploymentScore(),
            score.getLevel()
        );
    }

}

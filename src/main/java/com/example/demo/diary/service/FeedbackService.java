package com.example.demo.diary.service;

import com.example.demo.common.infrastructure.openai.OpenAiClient;
import com.example.demo.common.infrastructure.openai.dto.OpenAiResponse;
import com.example.demo.diary.controller.dto.FeedbackResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final OpenAiClient openAiClient;

    public FeedbackResult generateFeedback(String content) {
        try {
            OpenAiResponse response = openAiClient.getFeedback(content);
            return new FeedbackResult(response.getMessage(),
                Integer.parseInt(response.getCodeBlock().get("emotion-score")));
        } catch (Exception e) {
            return new FeedbackResult("피드백 생성중 오류가 발생하였습니다.", 0);
        }
    }
}

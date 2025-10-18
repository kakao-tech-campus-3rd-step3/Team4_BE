package com.example.demo.diary.service;

import com.example.demo.common.infrastructure.openai.OpenAiClient;
import com.example.demo.common.infrastructure.openai.dto.OpenAiResponse;
import com.example.demo.diary.controller.dto.FeedbackResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {

    private final OpenAiClient openAiClient;

    public FeedbackResult generateFeedback(String content) {
        try {
            OpenAiResponse response = openAiClient.getFeedback(content);
            return new FeedbackResult(response.getMessage(),
                Integer.parseInt(response.getCodeBlock().get("emotion-score")));
        } catch (Exception e) {
            log.error("OpenAiClient diary feedback exception", e);
            return new FeedbackResult("피드백 생성중 오류가 발생하였습니다.", 0);
        }
    }
}

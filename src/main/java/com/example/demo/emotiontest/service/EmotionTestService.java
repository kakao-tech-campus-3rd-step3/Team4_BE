package com.example.demo.emotiontest.service;

import com.example.demo.emotiontest.controller.dto.EmotionTestResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmotionTestService {

    private final EmotionTestRepository emotionTestRepository;

    public List<EmotionTestResponse> viewAll() {
        return emotionTestRepository.getAll().stream()
            .map(EmotionTestResponse::from)
            .toList();
    }

    public void applyResult() {
        // OpenAiClient 분석 결과 필요
        // 테스트 결과 -> 감정 반영
    }
}

package com.example.demo.diary.service;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.emotion.service.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRepository emotionRepository;

    private static final Float ALPHA = 0.5f; //조정 강도

    public void adjustSentimentEmotion(Integer score, Long userId) {
        if (score.equals(0)) {
            return;
        }

        Emotion emotion = emotionRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException());

        float normalized = normalize(score);
        int delta = calculate(normalized);

        emotion.adjust(EmotionType.SENTIMENT, delta);
        emotionRepository.save(emotion);
    }

    public int calculate(float normalized) {
        return 0;
    }

    private float normalize(Integer score) {
        return score / 10f;
    }
}

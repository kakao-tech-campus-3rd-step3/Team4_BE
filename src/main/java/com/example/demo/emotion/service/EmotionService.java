package com.example.demo.emotion.service;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRepository emotionRepository;

    public void adjustSentimentEmotion(Integer score, Long userId) {
        if (score.equals(0)) {
            return;
        }

        Emotion emotion = emotionRepository.findById(userId)
            .orElseThrow(RuntimeException::new);

        int delta = calculateDelta(emotion.getSentimentLevel(), score);

        emotion.adjust(EmotionType.SENTIMENT, delta);
        emotionRepository.save(emotion);
    }

    public int calculateDelta(Integer score, Integer input) {
        double a = 0.3;   // 최소 반영 비율 (30%)
        double b = 0.7;   // 감쇠 비율
        double k = 100.0; // 감쇠 강도

        double factor = a + b / Math.sqrt(1 + (double) score / k);
        int delta = (int) Math.round(input * factor);

        if (score + delta < 0) {
            delta = -score;
        }

        return delta;
    }

}

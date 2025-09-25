package com.example.demo.emotion.service;

import com.example.demo.emotion.controller.dto.EmotionTestResultDto;
import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmotionService {

    private final EmotionRepository emotionRepository;

    public void applyEmotionTestResult(User user, List<EmotionTestResultDto> result) {
        Emotion emotion = getByUser(user);

        result.forEach(r -> {
            EmotionType type = EmotionType.fromQuestionId(r.getQuestionId());
            int delta = r.getChoiceIndex() * 30;
            emotion.adjust(type, delta);
        });

        // 분석 결과 리턴?
    }

    @Transactional(readOnly = true)
    public Emotion getByUser(User user) {
        return emotionRepository.findById(user.getId())
            .orElseThrow();
    }
}

package com.example.demo.emotiontest.service;

import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.EmotionTestErrorCode;
import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.emotion.service.EmotionRepository;
import com.example.demo.emotiontest.controller.dto.EmotionTestResponse;
import com.example.demo.emotiontest.controller.dto.EmotionTestResultDto;
import com.example.demo.user.domain.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmotionTestService {

    private final EmotionTestRepository emotionTestRepository;
    private final EmotionRepository emotionRepository;

    @Transactional(readOnly = true)
    public List<EmotionTestResponse> viewAll() {
        return emotionTestRepository.getAll().stream()
            .map(EmotionTestResponse::from)
            .toList();
    }

    public void applyEmotionTestResult(User user, List<EmotionTestResultDto> result) {
        if (emotionRepository.existById(user.getId())) {
            throw new BusinessException(EmotionTestErrorCode.ALREADY_TESTED);
        }

        validateResult(result);

        Emotion emotion = emotionRepository.save(new Emotion(user.getId()));
        result.forEach(r -> {
            EmotionType type = EmotionType.fromQuestionId(r.getQuestionId());
            int delta = r.getChoiceIndex() * 30;
            emotion.adjust(type, delta);
        });
        emotionRepository.update(emotion);

        // 분석 결과 리턴?
    }

    private void validateResult(List<EmotionTestResultDto> result) {
        Set<Long> questionIds = new HashSet<>();
        result.forEach(r -> {
            questionIds.add(r.getQuestionId());
            if (r.getChoiceIndex() < 1 || 4 < r.getChoiceIndex()) {
                throw new BusinessException(EmotionTestErrorCode.ILLEGAL_CHOICE_INDEX);
            }
        });
        if (!questionIds.containsAll(Set.of(1L, 2L, 3L, 4L, 5L, 6L))) {
            throw new BusinessException(EmotionTestErrorCode.UNFINISHED_TEST_RESULT);
        }
    }
}

package com.example.demo.service;

import com.example.demo.common.converter.EmotionInputConverter;
import com.example.demo.domain.emotion.test.EmotionTestQuestion;
import com.example.demo.domain.user.EmotionInputVo;
import com.example.demo.domain.user.User;
import com.example.demo.dto.emotionTest.EmotionTestAnswersRequest;
import com.example.demo.dto.emotionTest.EmotionTestQuestionResponse;
import com.example.demo.repository.EmotionTestQuestionRepository;
import com.example.demo.repository.UserRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmotionTestService {

    private final EmotionTestQuestionRepository questionRepository;
    private volatile Map<Long, EmotionTestQuestion> cachedQuestionsMap;

    private Map<Long, EmotionTestQuestion> getQuestionsMap() {
        if (cachedQuestionsMap == null) {
            Map<Long, EmotionTestQuestion> ready = questionRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableMap(
                    EmotionTestQuestion::getId,
                    Function.identity()
                ));
            synchronized (this) {
                if (cachedQuestionsMap == null) {
                    cachedQuestionsMap = ready;
                }
            }
        }
        return cachedQuestionsMap;
    }

    @Transactional(readOnly = true)
    public List<EmotionTestQuestionResponse> getAll() {
        return getQuestionsMap().values().stream()
            .sorted(Comparator.comparing(EmotionTestQuestion::getId))
            .map(EmotionTestQuestionResponse::new)
            .toList();
    }

    public void applyResult(User user, List<EmotionTestAnswersRequest> answers) {
        if (user.getEmotion().isInitialized()) {
            throw new RuntimeException("이미 감정 테스트를 수행했음");
        }

        // validate answers
        Set<Long> allQuestionIds = getQuestionsMap().keySet();
        Set<Long> receivedIds = answers.stream()
            .map(EmotionTestAnswersRequest::getQuestionId)
            .collect(Collectors.toSet());
        if (!receivedIds.equals(allQuestionIds)) {
            throw new RuntimeException("모든 질문에 답변이 있어야함");
        }

        EmotionInputVo result = EmotionInputConverter.convert(answers);
        user.applyEmotionTestResult(result);
    }
}

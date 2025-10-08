package com.example.demo.diary.service;

import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.exception.errorcode.DiaryErrorCode;
import com.example.demo.diary.controller.dto.DiaryRequest;
import com.example.demo.diary.controller.dto.DiaryResponse;
import com.example.demo.diary.controller.dto.FeedbackResult;
import com.example.demo.diary.domain.Diary;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final FeedbackService feedbackService;
    private final EmotionService emotionService;

    public DiaryResponse createDiary(DiaryRequest request, User user) {
        Diary diary = new Diary(user, request.getEmotion(), request.getContent());
        FeedbackResult result = feedbackService.generateFeedback(request.getContent());
        diary.addFeedback(result.getFeedback());
        diary = diaryRepository.save(diary);
        try {
            emotionService.adjustSentimentEmotion(
                result.getScore(),
                user.getId()
            );
        } catch (Exception e) {
            log.error("Emotion adjust failed", e);
        }
        return new DiaryResponse(diary);
    }

    @Transactional(readOnly = true)
    public DiaryResponse getDiary(Long diaryId, User user) {
        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new BusinessException(DiaryErrorCode.DIARY_NOT_FOUND));

        if (!diary.isAuthor(user.getId())) {
            throw new BusinessException(DiaryErrorCode.DIARY_ACCESS_DENIED);
        }
        return new DiaryResponse(diary);
    }
}

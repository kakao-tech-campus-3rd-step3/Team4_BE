package com.example.demo.diary.service;

import com.example.demo.diary.controller.dto.DiaryEmotionResponse;
import com.example.demo.diary.controller.dto.DiaryRequest;
import com.example.demo.diary.controller.dto.DiaryResponse;
import com.example.demo.diary.controller.dto.FeedbackResult;
import com.example.demo.diary.domain.Diary;
import com.example.demo.emotion.service.EmotionService;
import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.DiaryErrorCode;
import com.example.demo.user.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Primary
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final FeedbackService feedbackService;
    private final EmotionService emotionService;

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public DiaryResponse getDiary(Long diaryId, User user) {
        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new BusinessException(DiaryErrorCode.DIARY_NOT_FOUND));

        if (!diary.isAuthor(user.getId())) {
            throw new BusinessException(DiaryErrorCode.DIARY_ACCESS_DENIED);
        }
        return new DiaryResponse(diary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaryEmotionResponse> getMonthlyDiaries(YearMonth yearMonth, User user) {
        LocalDate localDate = yearMonth.atDay(1);

        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.plusMonths(1).atStartOfDay().minusNanos(1);

        return diaryRepository.findAllByDateBetweenAndUserId(start, end, user.getId());
    }
}

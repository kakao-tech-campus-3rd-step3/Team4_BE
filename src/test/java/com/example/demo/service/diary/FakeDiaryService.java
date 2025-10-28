package com.example.demo.service.diary;

import com.example.demo.diary.controller.dto.DiaryEmotionResponse;
import com.example.demo.diary.controller.dto.DiaryRequest;
import com.example.demo.diary.controller.dto.DiaryResponse;
import com.example.demo.diary.domain.Diary;
import com.example.demo.diary.service.DiaryRepository;
import com.example.demo.diary.service.DiaryService;
import com.example.demo.emotion.service.EmotionService;
import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.DiaryErrorCode;
import com.example.demo.user.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FakeDiaryService implements DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private EmotionService emotionService;

    @Override
    public DiaryResponse createDiary(DiaryRequest request, User user) {
        Diary diary = new Diary(user, request.getEmotion(), request.getContent());
        diary.addFeedback("fake feedback");
        diary = diaryRepository.save(diary);
        try {
            emotionService.adjustSentimentEmotion(
                1,
                user.getId()
            );
        } catch (Exception e) {

        }
        return new DiaryResponse(diary);
    }

    @Override
    public DiaryResponse getDiary(Long diaryId, User user) {
        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new BusinessException(DiaryErrorCode.DIARY_NOT_FOUND));

        if (!diary.isAuthor(user.getId())) {
            throw new BusinessException(DiaryErrorCode.DIARY_ACCESS_DENIED);
        }
        return new DiaryResponse(diary);
    }

    @Override
    public List<DiaryEmotionResponse> getMonthlyDiaries(YearMonth yearMonth, User user) {
        LocalDate localDate = yearMonth.atDay(1);

        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.plusMonths(1).atStartOfDay().minusNanos(1);

        return diaryRepository.findAllByDateBetweenAndUserId(start, end, user.getId());
    }
}

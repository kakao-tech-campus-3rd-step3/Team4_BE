package com.example.demo.diary.service;

import com.example.demo.diary.controller.dto.DiaryEmotionResponse;
import com.example.demo.diary.controller.dto.DiaryRequest;
import com.example.demo.diary.controller.dto.DiaryResponse;
import com.example.demo.user.domain.User;
import java.time.YearMonth;
import java.util.List;

public interface DiaryService {
    DiaryResponse createDiary(DiaryRequest request, User user);
    DiaryResponse getDiary(Long diaryId, User user);
    List<DiaryEmotionResponse> getMonthlyDiaries(YearMonth yearMonth, User user);
}

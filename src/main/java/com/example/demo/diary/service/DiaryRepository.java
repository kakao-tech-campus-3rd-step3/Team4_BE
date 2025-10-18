package com.example.demo.diary.service;

import com.example.demo.diary.controller.dto.DiaryEmotionResponse;
import com.example.demo.diary.domain.Diary;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository {

    Diary save(Diary diary);

    Optional<Diary> findById(Long diaryId);

    List<DiaryEmotionResponse> findAllByDateBetween(LocalDateTime start, LocalDateTime end);
}

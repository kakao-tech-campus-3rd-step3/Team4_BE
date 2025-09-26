package com.example.demo.diary.service;

import com.example.demo.diary.domain.Diary;
import java.util.Optional;

public interface DiaryRepository {

    Diary save(Diary diary);

    Optional<Diary> findById(Long diaryId);
}

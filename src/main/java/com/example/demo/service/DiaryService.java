package com.example.demo.service;

import com.example.demo.domain.diary.Diary;
import com.example.demo.domain.user.User;
import com.example.demo.dto.diary.CreateDiaryResponse;
import com.example.demo.dto.diary.DiaryRequest;
import com.example.demo.dto.diary.DiaryResponse;
import com.example.demo.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public CreateDiaryResponse create(DiaryRequest request, User user) {
        Diary diary = new Diary(user, request.getEmotion(), request.getContent());
        diaryRepository.save(diary);
        return new CreateDiaryResponse(diary);
    }

    @Transactional(readOnly = true)
    public DiaryResponse get(Long diaryId, User user) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("일기를 찾을 수 없습니다."));

        if (!diary.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 일기만 조회할 수 있습니다.");
        }
        return new DiaryResponse(diary);
    }
}

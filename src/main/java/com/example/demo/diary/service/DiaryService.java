package com.example.demo.diary.service;

import com.example.demo.common.infrastructure.openai.OpenAiClient;
import com.example.demo.common.infrastructure.openai.dto.OpenAiResponse;
import com.example.demo.diary.controller.diary.CreateDiaryResponse;
import com.example.demo.diary.controller.diary.DiaryRequest;
import com.example.demo.diary.controller.diary.DiaryResponse;
import com.example.demo.diary.domain.Diary;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final OpenAiClient openAiClient;

    public CreateDiaryResponse createDiary(DiaryRequest request, User user) {
        Diary diary = new Diary(user, request.getEmotion(), request.getContent());
        OpenAiResponse feedback = openAiClient.getFeedback(request.getContent());
        diary.addFeedback(feedback.getMessage());
        diary = diaryRepository.save(diary);
        return new CreateDiaryResponse(diary);
    }

    @Transactional(readOnly = true)
    public DiaryResponse getDiary(Long diaryId, User user) {
        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new RuntimeException("다이어리를 찾을 수 없습니다"));

        diary.validateAuthor(user);
        return new DiaryResponse(diary);
    }
}

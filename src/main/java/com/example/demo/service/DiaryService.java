package com.example.demo.service;

import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.exception.errorcode.DiaryErrorCode;
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
                .orElseThrow(() -> new BusinessException(DiaryErrorCode.DIARY_NOT_FOUND));

        if (!diary.getAuthor().getId().equals(user.getId())) {
            throw new BusinessException(DiaryErrorCode.DIARY_ACCESS_DENIED);
        }
        return new DiaryResponse(diary);
    }
}

package com.example.demo.diary.infrastructure;

import com.example.demo.diary.controller.dto.DiaryEmotionResponse;
import com.example.demo.diary.domain.Diary;
import com.example.demo.diary.infrastructure.jpa.DiaryEntity;
import com.example.demo.diary.infrastructure.jpa.DiaryJpaRepository;
import com.example.demo.diary.service.DiaryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepository {

    private final DiaryJpaRepository diaryJpaRepository;

    @Override
    public Diary save(Diary diary) {
        return diaryJpaRepository.save(DiaryEntity.fromModel(diary)).toModel();
    }

    @Override
    public Optional<Diary> findById(Long diaryId) {
        return diaryJpaRepository.findById(diaryId).map(DiaryEntity::toModel);
    }

    @Override
    public List<DiaryEmotionResponse> findAllByDateBetweenAndUserId(
        LocalDateTime start,
        LocalDateTime end,
        Long userId
    ) {
        List<DiaryEntity> entities = diaryJpaRepository.findAllByCreatedAtBetweenAndAuthorId(
            start, end, userId
        );
        return entities.stream()
            .map(e -> new DiaryEmotionResponse(e.getId(), e.getEmotion(), e.getCreatedAt()))
            .toList();
    }
}

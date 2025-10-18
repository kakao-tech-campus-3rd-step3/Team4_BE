package com.example.demo.diary.controller.dto;

import com.example.demo.diary.domain.EmotionEnum;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class DiaryEmotionResponse {

    private final Long id;
    private final EmotionEnum emotion;
    private final LocalDateTime createdAt;

    public DiaryEmotionResponse(Long id, EmotionEnum emotion, LocalDateTime createdAt) {
        this.id = id;
        this.emotion = emotion;
        this.createdAt = createdAt;
    }
}

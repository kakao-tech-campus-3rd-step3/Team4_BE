package com.example.demo.diary.controller.dto;

import com.example.demo.diary.domain.Diary;
import com.example.demo.diary.domain.EmotionEnum;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class DiaryResponse {

    private final Long id;
    private final EmotionEnum emotion;
    private final String content;
    private final String feedback;
    private final LocalDateTime createdAt;

    public DiaryResponse(Diary diary) {
        this.id = diary.getId();
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
        this.feedback = diary.getFeedback().getContent();
        this.createdAt = diary.getCreatedAt();
    }
}

package com.example.demo.diary.controller.dto;

import com.example.demo.diary.domain.Diary;
import com.example.demo.diary.domain.EmotionEnum;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class DiaryResponse {

    private Long id;
    private EmotionEnum emotion;
    private String content;
    private String feedback;
    private LocalDateTime createdAt;

    public DiaryResponse(Diary diary) {
        this.id = diary.getId();
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
        this.feedback = diary.getFeedback().getContent();
        this.createdAt = diary.getCreatedAt();
    }
}

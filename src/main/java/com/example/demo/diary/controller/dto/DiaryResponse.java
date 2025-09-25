package com.example.demo.diary.controller.dto;

import com.example.demo.diary.domain.Diary;
import com.example.demo.diary.domain.EmotionEnum;
import com.example.demo.diary.domain.Feedback;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class DiaryResponse {

    private Long id;
    private EmotionEnum emotion;
    private String content;
    private Feedback feedback;
    private LocalDateTime createdAt;

    public DiaryResponse(Diary diary) {
        this.id = diary.getId();
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
        this.feedback = diary.getFeedback();
        this.createdAt = diary.getCreatedAt();
    }
}

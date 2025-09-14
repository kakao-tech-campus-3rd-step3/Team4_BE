package com.example.demo.dto.diary;

import com.example.demo.domain.diary.Diary;
import com.example.demo.domain.diary.DiaryFeedback;
import com.example.demo.domain.diary.EmotionEnum;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class DiaryResponse {

    private EmotionEnum emotion;
    private String content;
    private DiaryFeedback feedback;
    private LocalDateTime createdAt;

    public DiaryResponse(Diary diary) {
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
        this.feedback = diary.getFeedback();
        this.createdAt = diary.getCreatedAt();
    }
}

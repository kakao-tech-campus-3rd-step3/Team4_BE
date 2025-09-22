package com.example.demo.dto.diary;

import com.example.demo.domain.diary.Diary;
import com.example.demo.domain.diary.EmotionEnum;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class DiaryResponse {

    private EmotionEnum emotion;
    private String content;
    private String feedback;
    private LocalDateTime createdAt;

    public DiaryResponse(Diary diary) {
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
        if (diary.getFeedback() != null) {
            this.feedback = diary.getFeedback().getContent();
        }
        this.createdAt = diary.getCreatedAt();
    }
}

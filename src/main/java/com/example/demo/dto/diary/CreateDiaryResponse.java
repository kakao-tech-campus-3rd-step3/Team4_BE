package com.example.demo.dto.diary;

import com.example.demo.domain.diary.Diary;
import com.example.demo.domain.diary.EmotionEnum;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateDiaryResponse {

    private Long id;
    private EmotionEnum emotion;
    private String content;
    private String feedback;
    private LocalDateTime createdAt;

    public CreateDiaryResponse(Diary diary) {
        this.id = diary.getId();
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
        this.feedback = diary.getFeedback().getContent();
        this.createdAt = diary.getCreatedAt();
    }
}

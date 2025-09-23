package com.example.demo.diary.controller.diary;

import com.example.demo.diary.domain.Diary;
import com.example.demo.diary.domain.EmotionEnum;
import lombok.Getter;

@Getter
public class CreateDiaryResponse {

    private Long id;
    private EmotionEnum emotion;
    private String content;

    public CreateDiaryResponse(Diary diary) {
        this.id = diary.getId();
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
    }
}

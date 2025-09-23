package com.example.demo.diary.controller.diary;

import com.example.demo.diary.domain.EmotionEnum;
import lombok.Getter;

@Getter
public class DiaryRequest {

    private EmotionEnum emotion;
    private String content;

    public DiaryRequest(EmotionEnum emotion, String content) {
        this.emotion = emotion;
        this.content = content;
    }
}

package com.example.demo.dto.diary;

import com.example.demo.domain.diary.EmotionEnum;
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

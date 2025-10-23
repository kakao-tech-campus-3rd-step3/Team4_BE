package com.example.demo.diary.controller.dto;

import com.example.demo.diary.domain.EmotionEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DiaryRequest {

    @NotNull(message = "{diary.emotion.NotNull}")
    private EmotionEnum emotion;

    @NotBlank(message = "{diary.content.NotBlank}")
    @Size(max = 5000, message = "{diary.content.Size}")
    private String content;

    public DiaryRequest(EmotionEnum emotion, String content) {
        this.emotion = emotion;
        this.content = content;
    }
}

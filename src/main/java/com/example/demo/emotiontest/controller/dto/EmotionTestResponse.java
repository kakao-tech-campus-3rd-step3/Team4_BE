package com.example.demo.emotiontest.controller.dto;

import com.example.demo.emotiontest.domain.EmotionTest;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmotionTestResponse {

    private Long id;
    private String question;
    private List<String> answers;
    private String imageUrl;

    public static EmotionTestResponse from(EmotionTest e) {
        return new EmotionTestResponse(e.getId(), e.getQuestion(), List.copyOf(e.getAnswers()), e.getImageUrl());
    }
}

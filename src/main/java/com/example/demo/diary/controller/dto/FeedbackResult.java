package com.example.demo.diary.controller.dto;

import lombok.Getter;

@Getter
public class FeedbackResult {
    private final String feedback;
    private final Integer score;

    public FeedbackResult(String feedback, Integer score) {
        this.feedback = feedback;
        this.score = score;
    }
}

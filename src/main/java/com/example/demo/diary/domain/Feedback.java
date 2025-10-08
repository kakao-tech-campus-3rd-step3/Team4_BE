package com.example.demo.diary.domain;

import lombok.Getter;

@Getter
public class Feedback {

    private String content;

    public Feedback(String feedback) {
        this.content = feedback;
    }
}

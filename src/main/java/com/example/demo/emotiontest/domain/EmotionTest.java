package com.example.demo.emotiontest.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class EmotionTest {

    private Long id;
    private String question;
    private List<String> answers;
    private String imageUrl;

    public EmotionTest(Long id, String question, List<String> answers, String imageUrl) {
        this.id = id;
        this.question = question;
        this.answers = List.copyOf(answers);
        this.imageUrl = imageUrl;
    }
}

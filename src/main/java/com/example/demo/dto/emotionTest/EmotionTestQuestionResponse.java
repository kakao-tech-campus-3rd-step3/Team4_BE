package com.example.demo.dto.emotionTest;

import com.example.demo.domain.emotion.test.EmotionTestQuestion;
import java.util.List;
import lombok.Getter;

@Getter
public class EmotionTestQuestionResponse {

    private Long id;
    private String question;
    private List<String> answers;

    public EmotionTestQuestionResponse(EmotionTestQuestion emotionTestQuestion) {
        this.id = emotionTestQuestion.getId();
        this.question = emotionTestQuestion.getQuestion();
        this.answers = emotionTestQuestion.getAnswers();
    }
}

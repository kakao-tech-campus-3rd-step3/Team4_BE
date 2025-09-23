package com.example.demo.dto.emotionTest;

import com.example.demo.domain.emotion.test.EmotionTestQuestions;
import com.example.demo.domain.emotion.test.EmotionTestQuestions.EmotionTestQuestion;
import java.util.List;
import lombok.Getter;

@Getter
public class EmotionTestQuestionResponse {

    private Long id;
    private String question;
    private List<String> answers;
    private String imageUrl;

    public EmotionTestQuestionResponse(EmotionTestQuestion emotionTestQuestion) {
        this.id = emotionTestQuestion.getId();
        this.question = emotionTestQuestion.getQuestion();
        this.answers = emotionTestQuestion.getAnswers();
        this.imageUrl = emotionTestQuestion.getImageUrl();
    }
}

package com.example.demo.domain.emotionTest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Table(name = "emotion_test_question")
@Getter
public class EmotionTestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column
    private String answer1;

    @Column
    private String answer2;

    @Column
    private String answer3;

    @Column
    private String answer4;

    @Transient
    private List<String> answers;

    @PostLoad
    private void fillAnswers() {
        this.answers = new ArrayList<>();
        if (answer1 != null) {
            answers.add(answer1);
        }
        if (answer2 != null) {
            answers.add(answer2);
        }
        if (answer3 != null) {
            answers.add(answer3);
        }
        if (answer4 != null) {
            answers.add(answer4);
        }
    }

    protected EmotionTestQuestion() {
    }
}

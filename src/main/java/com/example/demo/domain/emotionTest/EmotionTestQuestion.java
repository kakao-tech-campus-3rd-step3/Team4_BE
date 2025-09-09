package com.example.demo.domain.emotionTest;

import jakarta.persistence.*;

@Entity
@Table(name = "emotion_test_question")
public class EmotionTestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    @Convert(converter = SelectionDataConverter.class)
    private SelectionData selectiondata;

    protected EmotionTestQuestion() {
    }
}

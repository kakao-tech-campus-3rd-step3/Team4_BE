package com.example.demo.domain.emotionTest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EmotionTestQuestion")
public class EmotionTestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String result;
    /** json
     * 선택지 데이터 내용
     * 1. 선택지 수
     * 2. 선택지 내용
     * 3. 선택지별 6가지 수치별 점수
     */

    @OneToMany(mappedBy = "emotionTestQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmotionTestQuestion> emotionTestQuestions = new ArrayList<>();

    protected EmotionTestQuestion() {
    }
}

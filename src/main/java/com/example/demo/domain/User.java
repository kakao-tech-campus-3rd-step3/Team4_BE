package com.example.demo.domain;

import com.example.demo.domain.emotionTest.EmotionTestResult;
import com.example.demo.domain.userEmotion.UserEmotion;
import com.example.demo.domain.userEmotion.UserEmotionHistory;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer point;

    @Column
    private String catName;

    @Column(nullable = false)
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserEmotionHistory> userEmotionHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmotionTestResult> emotionTestResults = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserEmotion userEmotions;

    protected User() {
    }
}

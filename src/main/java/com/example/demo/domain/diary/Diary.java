package com.example.demo.domain.diary;

import com.example.demo.domain.common.BaseEntity;
import com.example.demo.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "diary")
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmotionEnum emotion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiaryFeedback feedback;

    protected Diary() {
    }

    public Diary(User author, EmotionEnum emotion, String content) {
        this.author = author;
        this.emotion = emotion;
        this.content = content;
    }

    public void addFeedback(String feedbackContent) {
        this.feedback = new DiaryFeedback(this, feedbackContent);
    }
}

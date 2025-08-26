package com.example.demo.domain.diary;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.User;
import jakarta.persistence.*;

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
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private EmotionEnum emotion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiaryFeedback feedback;

    protected Diary() {
    }

    private Diary(User author, EmotionEnum emotion, String content) {
        this.author = author;
        this.emotion = emotion;
        this.content = content;
    }

    public static Diary of(User author, EmotionEnum emotion, String content) {
        return new Diary(author, emotion, content);
    }

    public void addFeedback(String feedbackContent) {
        this.feedback = DiaryFeedback.of(this, feedbackContent);
    }
}

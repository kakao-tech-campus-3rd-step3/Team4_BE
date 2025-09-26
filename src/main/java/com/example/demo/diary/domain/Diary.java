package com.example.demo.diary.domain;

import com.example.demo.user.domain.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Diary {

    private Long id;
    private User author;
    private EmotionEnum emotion;
    private String content;
    private Feedback feedback;
    private LocalDateTime createdAt;

    public Diary(Long id, User author, EmotionEnum emotion, String content, Feedback feedback, LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.emotion = emotion;
        this.content = content;
        this.feedback = feedback;
        this.createdAt = createdAt;
    }

    public Diary(User user, EmotionEnum emotion, String content) {
        this(null, user, emotion, content, null, null);
    }

    public boolean isAuthor(Long userId) {
        return author.getId().equals(userId);
    }

    public void addFeedback(String feedback) {
        this.feedback = new Feedback(feedback);
    }
}

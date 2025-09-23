package com.example.demo.diary.infrastructure.jpa;

import com.example.demo.common.infrastructure.jpa.BaseEntity;
import com.example.demo.diary.domain.Diary;
import com.example.demo.diary.domain.EmotionEnum;
import com.example.demo.diary.domain.Feedback;
import com.example.demo.user.infrastructure.jpa.UserEntity;
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
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "diary")
public class DiaryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmotionEnum emotion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private String feedback;

    protected DiaryEntity() {
    }

    private DiaryEntity(Long id, UserEntity author, EmotionEnum emotion, String content,
        String feedback) {
        this.id = id;
        this.author = author;
        this.emotion = emotion;
        this.content = content;
        this.feedback = feedback;
    }

    public Diary toModel() {
        return new Diary(id, author.toModel(), emotion, content, new Feedback(feedback), getCreatedAt());
    }

    public static DiaryEntity fromModel(Diary diary) {
        return new DiaryEntity(diary.getId(), UserEntity.fromModel(diary.getAuthor()), diary.getEmotion(),
            diary.getContent(), diary.getFeedback().getAsString());
    }
}

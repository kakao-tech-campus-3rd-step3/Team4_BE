package com.example.demo.user.infrastructure.jpa.emotion;

import com.example.demo.common.infrastructure.jpa.BaseEntity;
import com.example.demo.user.infrastructure.jpa.UserEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_emotion_history")
public class UserEmotionHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Embedded
    private EmotionScore emotionScore;

    protected UserEmotionHistory() {
    }
}

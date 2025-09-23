package com.example.demo.user.infrastructure.jpa.emotion;

import com.example.demo.user.infrastructure.jpa.UserEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_emotion")
public class UserEmotionEntity {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Embedded
    private EmotionScore emotionScore;

    protected UserEmotionEntity() {
    }

    public UserEmotionEntity(UserEntity userEntity, EmotionScore emotionScore) {
        this.userEntity = userEntity;
        this.emotionScore = emotionScore;
    }

}

package com.example.demo.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private String refreshToken;

    @Embedded
    private Emotion emotion;

    private User(String name, String email, Integer point, String refreshToken) {
        this.name = name;
        this.email = email;
        this.point = point;
        this.refreshToken = refreshToken;
        emotion = new Emotion();
    }

    public static User of(String name, String email) {
        return new User(name, email, 0, "");
    }

    public void applyEmotionTestResult(EmotionInputVo result) {
        this.emotion = new Emotion(result);
    }

    public User update(String name) {
        this.name = name;
        return this;
    }

    public void spendPoints(Integer point) {
        this.point -= point;
    }

    public void earnPoints(Integer point) {
        this.point += point;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
}

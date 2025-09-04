package com.example.demo.domain.userEmotion;

import com.example.demo.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_emotion")
public class UserEmotion {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer sentimentLevel;

    @Column(nullable = false)
    private Integer energyLevel;

    @Column(nullable = false)
    private Integer cognitiveLevel;

    @Column(nullable = false)
    private Integer relationshipLevel;

    @Column(nullable = false)
    private Integer stressLevel;

    @Column(nullable = false)
    private Integer employmentLevel;

    protected UserEmotion() {
    }
}

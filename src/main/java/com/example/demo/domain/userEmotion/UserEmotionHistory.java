package com.example.demo.domain.userEmotion;

import com.example.demo.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "UserEmotionHistory")
public class UserEmotionHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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

    protected UserEmotionHistory() {
    }
}

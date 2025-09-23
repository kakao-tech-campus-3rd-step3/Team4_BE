package com.example.demo.user.infrastructure.jpa.emotion;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EmotionScore {

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

}

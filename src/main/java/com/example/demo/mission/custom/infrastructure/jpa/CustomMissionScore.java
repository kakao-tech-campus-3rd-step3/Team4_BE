package com.example.demo.mission.custom.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class CustomMissionScore {

    @Column
    private Integer sentimentScore;

    @Column
    private Integer energyScore;

    @Column
    private Integer cognitiveScore;

    @Column
    private Integer relationshipScore;

    @Column
    private Integer stressScore;

    @Column
    private Integer employmentScore;

    protected CustomMissionScore() {
    }

    public CustomMissionScore(Integer sentimentScore, Integer energyScore, Integer cognitiveScore,
            Integer relationshipScore, Integer stressScore, Integer employmentScore) {
        this.sentimentScore = sentimentScore;
        this.energyScore = energyScore;
        this.cognitiveScore = cognitiveScore;
        this.relationshipScore = relationshipScore;
        this.stressScore = stressScore;
        this.employmentScore = employmentScore;
    }
}

package com.example.demo.mission.regular.infrastructure.jpa;

import com.example.demo.mission.regular.domain.score.MissionScores;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class MissionScoreEmbeddable {

    @Column(nullable = false)
    private Integer sentimentScore;

    @Column(nullable = false)
    private Integer energyScore;

    @Column(nullable = false)
    private Integer cognitiveScore;

    @Column(nullable = false)
    private Integer relationshipScore;

    @Column(nullable = false)
    private Integer stressScore;

    @Column(nullable = false)
    private Integer employmentScore;

    protected MissionScoreEmbeddable() {
    }

    public MissionScores toModel() {
        return new MissionScores(sentimentScore, energyScore, cognitiveScore, relationshipScore,
            stressScore, employmentScore);
    }

}

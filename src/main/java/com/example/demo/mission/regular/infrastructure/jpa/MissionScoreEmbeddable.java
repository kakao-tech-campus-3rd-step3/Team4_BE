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

    private MissionScoreEmbeddable(Integer sentimentScore, Integer energyScore,
        Integer cognitiveScore, Integer relationshipScore, Integer stressScore,
        Integer employmentScore) {
        this.sentimentScore = sentimentScore;
        this.energyScore = energyScore;
        this.cognitiveScore = cognitiveScore;
        this.relationshipScore = relationshipScore;
        this.stressScore = stressScore;
        this.employmentScore = employmentScore;
    }

    protected MissionScoreEmbeddable() {
    }

    public MissionScores toModel() {
        return new MissionScores(sentimentScore, energyScore, cognitiveScore, relationshipScore,
            stressScore, employmentScore);
    }

}

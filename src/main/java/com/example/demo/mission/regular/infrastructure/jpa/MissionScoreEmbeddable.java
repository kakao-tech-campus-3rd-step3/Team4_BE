package com.example.demo.mission.regular.infrastructure.jpa;

import com.example.demo.mission.regular.domain.MissionScore;
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

    private MissionScoreEmbeddable(Integer sentimentScore, Integer energyScore, Integer cognitiveScore, Integer relationshipScore, Integer stressScore, Integer employmentScore) {
        this.sentimentScore = sentimentScore;
        this.energyScore = energyScore;
        this.cognitiveScore = cognitiveScore;
        this.relationshipScore = relationshipScore;
        this.stressScore = stressScore;
        this.employmentScore = employmentScore;
    }

    protected MissionScoreEmbeddable() {}

    public MissionScore toModel() {
        return new MissionScore(sentimentScore, energyScore, cognitiveScore, relationshipScore, stressScore, employmentScore);
    }

    public static MissionScoreEmbeddable fromModel(MissionScore missionScore) {
        return new MissionScoreEmbeddable(missionScore.getSentimentScore(), missionScore.getEnergyScore(), missionScore.getCognitiveScore(), missionScore.getRelationshipScore(), missionScore.getStressScore(), missionScore.getEmploymentScore());
    }

}

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

}

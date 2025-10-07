package com.example.demo.mission.regular.service.score;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MissionScoreMinMax {

    private final Integer sentimentMin;
    private final Integer sentimentMax;
    private final Integer energyMin;
    private final Integer energyMax;
    private final Integer cognitiveMin;
    private final Integer cognitiveMax;
    private final Integer relationshipMin;
    private final Integer relationshipMax;
    private final Integer stressMin;
    private final Integer stressMax;
    private final Integer employmentMin;
    private final Integer employmentMax;
}

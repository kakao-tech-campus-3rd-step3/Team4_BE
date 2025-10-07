package com.example.demo.mission.regular.service.score;

import lombok.Getter;

@Getter
public class MissionNormalization {

    private final Integer sentimentNormalization;
    private final Integer energyNormalization;
    private final Integer cognitiveNormalization;
    private final Integer relationshipNormalization;
    private final Integer stressNormalization;
    private final Integer employmentNormalization;

    public MissionNormalization(Integer sentimentNormalization, Integer energyNormalization,
            Integer cognitiveNormalization,
            Integer relationshipNormalization, Integer stressNormalization,
            Integer employmentNormalization) {
        this.sentimentNormalization = sentimentNormalization;
        this.energyNormalization = energyNormalization;
        this.cognitiveNormalization = cognitiveNormalization;
        this.relationshipNormalization = relationshipNormalization;
        this.stressNormalization = stressNormalization;
        this.employmentNormalization = employmentNormalization;
    }
}

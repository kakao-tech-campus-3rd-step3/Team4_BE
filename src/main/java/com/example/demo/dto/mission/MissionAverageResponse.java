package com.example.demo.dto.mission;

import lombok.Getter;

@Getter
public class MissionAverageResponse {

    private Double sentimentAvg;
    private Double energyAvg;
    private Double cognitiveAvg;
    private Double relationshipAvg;
    private Double stressAvg;

    public MissionAverageResponse(Double sentimentAvg, Double energyAvg, Double cognitiveAvg, Double relationshipAvg, Double stressAvg) {
        this.sentimentAvg = sentimentAvg;
        this.energyAvg = energyAvg;
        this.cognitiveAvg = cognitiveAvg;
        this.relationshipAvg = relationshipAvg;
        this.stressAvg = stressAvg;
    }
}

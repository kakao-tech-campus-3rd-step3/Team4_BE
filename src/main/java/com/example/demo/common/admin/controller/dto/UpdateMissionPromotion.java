package com.example.demo.common.admin.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMissionPromotion {

    private String content;
    private MissionCategoryEnum category;
    private Integer sentimentScore;
    private Integer energyScore;
    private Integer cognitiveScore;
    private Integer relationshipScore;
    private Integer stressScore;
    private Integer employmentScore;
    private Integer level;

    public UpdateMissionPromotion() {
    }

    public UpdateMissionPromotion(String content, MissionCategoryEnum category,
        Integer sentimentScore, Integer energyScore, Integer cognitiveScore,
        Integer relationshipScore,
        Integer stressScore, Integer employmentScore, Integer level) {
        this.content = content;
        this.category = category;
        this.sentimentScore = sentimentScore;
        this.energyScore = energyScore;
        this.cognitiveScore = cognitiveScore;
        this.relationshipScore = relationshipScore;
        this.stressScore = stressScore;
        this.employmentScore = employmentScore;
        this.level = level;
    }
}

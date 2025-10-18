package com.example.demo.common.admin.domain;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import lombok.Getter;

@Getter
public class MissionPromotion {

    private final Long id;
    private String content;
    private MissionCategoryEnum category;
    private MissionPromotionScore score;
    private Integer level;
    private CustomMissionStateEnum state;

    public MissionPromotion(Long id, String content, MissionCategoryEnum category,
            Integer level, MissionPromotionScore score, CustomMissionStateEnum state) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.score = score;
        this.level = level;
        this.state = state;
    }

    public MissionPromotion(String content, MissionCategoryEnum category) {
        this(null, content, category, null, null, CustomMissionStateEnum.WAITING);
    }

    public void filtered(Integer sentimentScore, Integer energyScore,
        Integer cognitiveScore, Integer relationshipScore, Integer stressScore,
        Integer employmentScore, Integer level) {
        this.score = new MissionPromotionScore(sentimentScore, energyScore, cognitiveScore,
            relationshipScore, stressScore, employmentScore);
        this.level = level;
        this.state = CustomMissionStateEnum.FILTERED;
    }

    public void promoted() {
        this.state = CustomMissionStateEnum.PROMOTED;    }

    public void rejected() {
        this.state = CustomMissionStateEnum.REJECTED;
    }

    public void update(String content, MissionCategoryEnum category,
        Integer sentimentScore, Integer energyScore, Integer cognitiveScore,
        Integer relationshipScore,
        Integer stressScore, Integer employmentScore, Integer level) {
        this.content = content;
        this.category = category;
        this.score = new MissionPromotionScore(sentimentScore, energyScore, cognitiveScore,
            relationshipScore, stressScore, employmentScore);
        this.level = level;
    }

}
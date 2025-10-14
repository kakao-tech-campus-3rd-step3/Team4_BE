package com.example.demo.common.admin.domain;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionStateEnum;
import lombok.Getter;

@Getter
public class MissionPromotion {

    private Long id;
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
}
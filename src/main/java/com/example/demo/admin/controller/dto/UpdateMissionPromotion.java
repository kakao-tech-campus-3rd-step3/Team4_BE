package com.example.demo.admin.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class UpdateMissionPromotion {

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;

    @NotNull(message = "카테고리는 필수 항목입니다.")
    private MissionCategoryEnum category;

    @NotNull(message = "감정 점수는 필수 항목입니다.")
    @Range(min = 0, max = 10, message = "{admin.score.Range}")
    private Integer sentimentScore;

    @NotNull(message = "에너지 점수는 필수 항목입니다.")
    @Range(min = 0, max = 10, message = "{admin.score.Range}")
    private Integer energyScore;

    @NotNull(message = "인지 점수는 필수 항목입니다.")
    @Range(min = 0, max = 10, message = "{admin.score.Range}")
    private Integer cognitiveScore;

    @NotNull(message = "관계 점수는 필수 항목입니다.")
    @Range(min = 0, max = 10, message = "{admin.score.Range}")
    private Integer relationshipScore;

    @NotNull(message = "스트레스 점수는 필수 항목입니다.")
    @Range(min = 0, max = 10, message = "{admin.score.Range}")
    private Integer stressScore;

    @NotNull(message = "취업 점수는 필수 항목입니다.")
    @Range(min = 0, max = 10, message = "{admin.score.Range}")
    private Integer employmentScore;

    @NotNull(message = "난이도는 필수 항목입니다.")
    @Range(min = 0, max = 10, message = "{admin.level.Range}")
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

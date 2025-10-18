package com.example.demo.common.admin.controller.dto;

import com.example.demo.mission.MissionCategoryEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMissionPromotion {

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;

    @NotNull(message = "카테고리는 필수 항목입니다.")
    private MissionCategoryEnum category;

    @NotNull(message = "감정 점수는 필수 항목입니다.")
    @Min(value = 0, message = "감정 점수는 0 이상입니다.")
    @Max(value = 10, message = "감정 점수는 10 이하입니다.")
    private Integer sentimentScore;

    @NotNull(message = "에너지 점수는 필수 항목입니다.")
    @Min(value = 0, message = "에너지 점수는 0 이상입니다.")
    @Max(value = 10, message = "에너지 점수는 10 이하입니다.")
    private Integer energyScore;

    @NotNull(message = "인지 점수는 필수 항목입니다.")
    @Min(value = 0, message = "인지 점수는 0 이상입니다.")
    @Max(value = 10, message = "인지 점수는 10 이하입니다.")
    private Integer cognitiveScore;

    @NotNull(message = "관계 점수는 필수 항목입니다.")
    @Min(value = 0, message = "관계 점수는 0 이상입니다.")
    @Max(value = 10, message = "관계 점수는 10 이하입니다.")
    private Integer relationshipScore;

    @NotNull(message = "스트레스 점수는 필수 항목입니다.")
    @Min(value = 0, message = "스트레스 점수는 0 이상입니다.")
    @Max(value = 10, message = "스트레스 점수는 10 이하입니다.")
    private Integer stressScore;

    @NotNull(message = "취업 점수는 필수 항목입니다.")
    @Min(value = 0, message = "취업 점수는 0 이상입니다.")
    @Max(value = 10, message = "취업 점수는 10 이하입니다.")
    private Integer employmentScore;

    @NotNull(message = "난이도는 필수 항목입니다.")
    @Min(value = 0, message = "난이도는 0 이상입니다.")
    @Max(value = 10, message = "난이도는 10 이하입니다.")
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

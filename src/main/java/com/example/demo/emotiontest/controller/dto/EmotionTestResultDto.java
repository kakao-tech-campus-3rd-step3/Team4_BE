package com.example.demo.emotiontest.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmotionTestResultDto {

    @NotNull
    @Min(value = 1, message = "{emotionTest.questionId.Min}")
    @Max(value = 6, message = "{emotionTest.questionId.Max}")
    private Long questionId;

    @NotNull
    @Min(value = 1, message = "{emotionTest.choiceIndex.Min}")
    @Max(value = 4, message = "{emotionTest.choiceIndex.Max}")
    private Integer choiceIndex;
}

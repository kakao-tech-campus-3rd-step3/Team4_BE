package com.example.demo.emotiontest.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class EmotionTestRequest {

    @Valid
    @Size(min = 6, max = 6, message = "{emotionTest.list.Size}")
    private List<EmotionTestResultDto> request;
}

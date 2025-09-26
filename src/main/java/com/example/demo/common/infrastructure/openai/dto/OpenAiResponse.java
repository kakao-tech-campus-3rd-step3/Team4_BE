package com.example.demo.common.infrastructure.openai.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OpenAiResponse {

    private String message;
    private Map<String, String> codeBlock;
}

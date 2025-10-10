package com.example.demo.common.infrastructure.openai.dto;

import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OpenAiResponse {

    private String message;
    private Map<String, String> codeBlock;

    public Optional<Integer> getEmotionScore() {
        String value = codeBlock.get("emotion-score");
        if (value == null) {
            return Optional.empty();
        }
        else {
            return Optional.of(Integer.parseInt(value));
        }
    }

    public Optional<Integer> getDangerScore() {
        String value = codeBlock.get("danger-score");
        if (value == null) {
            return Optional.empty();
        }
        else {
            return Optional.of(Integer.parseInt(value));
        }
    }
}

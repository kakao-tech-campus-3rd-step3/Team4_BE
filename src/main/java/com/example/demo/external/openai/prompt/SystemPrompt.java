package com.example.demo.external.openai.prompt;

import lombok.Getter;

@Getter
public enum SystemPrompt {
    DIARY_FEEDBACK(""),
    CONVERSATION_CAT("");

    private final String content;
    private final double temperature;
    private final int maxToken;

    SystemPrompt(String content, double temperature, int maxToken) {
        this.content = content;
        this.temperature = temperature;
        this.maxToken = maxToken;
    }

    SystemPrompt(String content) {
        this(content, 0.7, 500);
    }
}

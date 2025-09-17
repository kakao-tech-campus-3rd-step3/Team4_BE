package com.example.demo.external.openai.prompt;

import com.example.demo.external.openai.dto.ChatCompletionRequest;
import java.util.List;

public class PromptManager {

    public static ChatCompletionRequest diaryFeedback(String userMessage) {
        return ChatCompletionRequest.builder()
            .model("gpt-4o-mini")
            .messages(List.of(
                systemMessage(SystemPrompt.DIARY_FEEDBACK.getContent()),
                userMessage(userMessage)
            ))
            .temperature(SystemPrompt.DIARY_FEEDBACK.getTemperature())
            .max_tokens(SystemPrompt.DIARY_FEEDBACK.getMaxToken())
            .build();
    }

    private static ChatCompletionRequest.Message systemMessage(String content) {
        return ChatCompletionRequest.Message.builder()
            .role("system")
            .content(content)
            .build();
    }

    private static ChatCompletionRequest.Message userMessage(String content) {
        return ChatCompletionRequest.Message.builder()
            .role("user")
            .content(content)
            .build();
    }

    private static ChatCompletionRequest.Message assistMessage(String content) {
        return ChatCompletionRequest.Message.builder()
            .role("assist") //
            .content(content)
            .build();
    }
}

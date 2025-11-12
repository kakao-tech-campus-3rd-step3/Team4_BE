package com.example.demo.openai.helper;

import com.example.demo.openai.PromptManager;
import com.example.demo.openai.dto.ChatCompletionRequest;
import com.example.demo.openai.dto.ChatCompletionRequest.Message;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChatCompletionRequestFactory {

    private final String model;
    private final double temperature;
    private final int maxToken;
    private final PromptManager promptManager;

    public ChatCompletionRequestFactory(
        @Value("${openai.parameter.model}") String model,
        @Value("${openai.parameter.temperature}") double temperature,
        @Value("${openai.parameter.max-token}") int maxToken,
        PromptManager promptManager
    ) {
        this.model = model;
        this.temperature = temperature;
        this.maxToken = maxToken;
        this.promptManager = promptManager;
    }

    public ChatCompletionRequest buildDiaryFeedbackRequest(String entry) {
        return singleEntry(promptManager.getDiaryFeedbackBaseMessages(), entry);
    }

    public ChatCompletionRequest buildMyCatChatRequest(String entry, List<String> context, String memory) {
        List<String> entries = new ArrayList<>(context);
        entries.add(entry);
        return multiEntry(promptManager.getMyCatChatBaseMessages(memory), entries);
    }

    public ChatCompletionRequest buildCustomMissionEvaluateRequest(String entry) {
        return singleEntry(promptManager.getCustomMissionEvaluateBaseMessages(), entry);
    }

    public ChatCompletionRequest buildMemoryExtractorRequest(String memory, List<String> context) {
        return multiEntry(promptManager.getChatMemoryExtractorBaseMessages(memory), context);
    }

    private ChatCompletionRequest singleEntry(List<Message> baseMessages, String entry) {
        baseMessages.add(
            Message.builder()
                .role("user")
                .content(entry)
                .build()
        );

        return ChatCompletionRequest.builder()
            .model(model)
            .temperature(temperature)
            .max_tokens(maxToken)
            .messages(baseMessages)
            .build();
    }

    private ChatCompletionRequest multiEntry(List<Message> baseMessages, List<String> entries) {
        for (int i = 0; i < entries.size(); i++) {
            String role = i % 2 == 0 ? "user" : "assistant";
            baseMessages.add(
                Message.builder()
                    .role(role)
                    .content(entries.get(i))
                    .build()
            );
        }

        return ChatCompletionRequest.builder()
            .model(model)
            .temperature(temperature)
            .max_tokens(maxToken)
            .messages(baseMessages)
            .build();
    }
}

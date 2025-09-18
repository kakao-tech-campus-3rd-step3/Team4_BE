package com.example.demo.external.openai;

import static com.example.demo.external.openai.PromptManager.DIARY_FEEDBACK_BASE_MESSAGE;

import com.example.demo.external.openai.dto.ChatCompletionRequest;
import java.util.ArrayList;
import java.util.List;

public class ChatCompletionRequestBuilder {

    private static final String MODEL = "gpt-4o-mini";
    private static final double TEMPERATURE = 0.7;
    private static final int MAX_TOKEN = 500;

    public static ChatCompletionRequest buildDiaryFeedbackRequest(String userEntry) {
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(MODEL);

        List<ChatCompletionRequest.Message> message = new ArrayList<>(DIARY_FEEDBACK_BASE_MESSAGE);
        message.add(userMessage(userEntry));
        request.setMessages(message);

        request.setTemperature(TEMPERATURE);
        request.setMax_tokens(MAX_TOKEN);

        return request;
    }

    private static ChatCompletionRequest.Message userMessage(String content) {
        return ChatCompletionRequest.Message.builder()
            .role("user")
            .content(content)
            .build();
    }

    private static ChatCompletionRequest.Message assistantMessage(String content) {
        return ChatCompletionRequest.Message.builder()
            .role("assistant")
            .content(content)
            .build();
    }
}

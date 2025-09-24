package com.example.demo.common.infrastructure.openai.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;
    private Integer max_tokens;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {

        private String role;
        private String content;
    }
}

package com.example.demo.common.infrastructure.openai.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatCompletionRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;
    private Integer max_tokens;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Message {

        private String role;
        private String content;
    }
}

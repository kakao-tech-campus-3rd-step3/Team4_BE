package com.example.demo.external.openai.dto;

import java.util.List;
import lombok.Data;

@Data
public class ChatCompletionResponse {

    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;

    @Data
    public static class Choice {

        private int index;
        private Message message;
        private String finish_reason;
    }

    @Data
    public static class Message {

        private String role;
        private String content;
    }

    @Data
    public static class Usage {

        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }
}

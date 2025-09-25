package com.example.demo.chat.controller.dto;

import com.example.demo.chat.infrastructure.jpa.Sender;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ChatHistoryResponse {

    private Sender sender;
    private String content;
    private LocalDateTime createdAt;

    public ChatHistoryResponse(String content, Sender sender, LocalDateTime createdAt) {
        this.content = content;
        this.sender = sender;
        this.createdAt = createdAt;
    }
}

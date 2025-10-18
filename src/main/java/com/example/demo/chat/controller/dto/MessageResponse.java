package com.example.demo.chat.controller.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MessageResponse {

    private final Long chatId;
    private final String message;
    private final String reply;
    private final LocalDateTime createdAt;

    public MessageResponse(Long chatId, String message, String reply, LocalDateTime createdAt) {
        this.chatId = chatId;
        this.message = message;
        this.reply = reply;
        this.createdAt = createdAt;
    }
    
}

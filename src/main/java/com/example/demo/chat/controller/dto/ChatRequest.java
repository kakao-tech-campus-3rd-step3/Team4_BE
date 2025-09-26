package com.example.demo.chat.controller.dto;

import lombok.Getter;

@Getter
public class ChatRequest {

    private String message;

    public ChatRequest(String message) {
        this.message = message;
    }
}

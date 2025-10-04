package com.example.demo.chat.controller.dto;

import lombok.Getter;

@Getter
public class ChatResponse {

    private String message;

    public ChatResponse(String message) {
        this.message = message;
    }

}

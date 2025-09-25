package com.example.demo.chat.controller.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ChatListResponse {

    private List<ChatHistoryResponse> chats;
    private int totalPages;
    private long totalElements;

    public ChatListResponse(List<ChatHistoryResponse> chats, int totalPages, long totalElements) {
        this.chats = chats;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}

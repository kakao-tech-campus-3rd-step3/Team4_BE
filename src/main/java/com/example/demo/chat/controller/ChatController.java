package com.example.demo.chat.controller;

import com.example.demo.chat.controller.dto.ChatListResponse;
import com.example.demo.chat.controller.dto.ChatRequest;
import com.example.demo.chat.controller.dto.ChatResponse;
import com.example.demo.chat.service.ChatService;
import com.example.demo.common.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request,
            @CurrentUser User user) {
        ChatResponse response = chatService.chat(request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ChatListResponse> getChatHistory(
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "0") Integer page,
            @CurrentUser User user
    ) {
        ChatListResponse response = chatService.getChatHistory(user.getId(), page, size);
        return ResponseEntity.ok(response);
    }

}

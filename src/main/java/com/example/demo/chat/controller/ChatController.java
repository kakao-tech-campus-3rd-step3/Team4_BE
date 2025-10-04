package com.example.demo.chat.controller;

import com.example.demo.chat.controller.dto.ChatRequest;
import com.example.demo.chat.controller.dto.ChatResponse;
import com.example.demo.chat.controller.dto.MessageResponse;
import com.example.demo.chat.service.ChatService;
import com.example.demo.common.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponse> postMessage(@RequestBody ChatRequest request,
        @CurrentUser User user) {
        return ResponseEntity.ok(chatService.postMessage(request.getMessage(), user));
    }

    @GetMapping
    public ResponseEntity<Page<MessageResponse>> getMessages(
        @CurrentUser User user,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(chatService.getMessages(user, page, size));
    }
}
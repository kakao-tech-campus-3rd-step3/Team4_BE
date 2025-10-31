package com.example.demo.chat.service;

import com.example.demo.chat.controller.dto.ChatResponse;
import com.example.demo.chat.controller.dto.MessageResponse;
import com.example.demo.chat.infrastructure.jpa.ChatMemory;
import com.example.demo.chat.infrastructure.jpa.Message;
import com.example.demo.chat.infrastructure.jpa.Sender;
import com.example.demo.emotion.domain.DangerState;
import com.example.demo.emotion.service.EmotionService;
import com.example.demo.openai.OpenAiClient;
import com.example.demo.openai.dto.OpenAiResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private static final int CONTEXT_SIZE = 10;

    private final OpenAiClient openAiClient;
    private final MessageRepository messageRepository;
    private final EmotionService emotionService;
    private final ChatMemoryService chatMemoryService;

    public ChatResponse postMessage(String messageContent, Long userId) {
        List<String> context = fetchContext(userId);
        ChatMemory memory = chatMemoryService.get(userId);
        OpenAiResponse openAiResponse = openAiClient.getChatResponse(messageContent, context, memory.getMemory());

        openAiResponse.getDangerScore().ifPresent(dangerScore -> {
            DangerState state = emotionService.applyAndGetDangerState(userId, dangerScore);
            state.adjust(memory);
        });

        Integer dangerScore = openAiResponse.getDangerScore().orElseGet(() -> null);
        Message userMessage = new Message(userId, Sender.USER, messageContent, dangerScore,
            LocalDateTime.now());
        messageRepository.save(userMessage);
        Message catMessage = new Message(userId, Sender.CAT, openAiResponse.getMessage(), dangerScore,
            LocalDateTime.now());
        messageRepository.save(catMessage);

        if (messageRepository.countByUserId(userId) % CONTEXT_SIZE == 0) {
            context = fetchContext(userId);
            chatMemoryService.callUpdate(userId, context);
        }

        return new ChatResponse(openAiResponse.getMessage());
    }

    @Transactional(readOnly = true)
    public Page<MessageResponse> getMessages(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findByUserId(userId, pageable)
            .map(message -> new MessageResponse(message.getId(), message.getContent(), null,
                message.getCreatedAt()));
    }

    private List<String> fetchContext(Long userId) {
        List<String> context = messageRepository.findTopNByUserIdOrderByCreatedAtDesc(userId, CONTEXT_SIZE).stream()
            .map(Message::getContent)
            .collect(Collectors.toList());
        Collections.reverse(context);
        return context;
    }
}
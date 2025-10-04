package com.example.demo.chat.service;

import com.example.demo.chat.controller.dto.ChatResponse;
import com.example.demo.chat.controller.dto.MessageResponse;
import com.example.demo.chat.infrastructure.jpa.Message;
import com.example.demo.chat.infrastructure.jpa.Sender;
import com.example.demo.common.infrastructure.openai.OpenAiClient;
import com.example.demo.common.infrastructure.openai.dto.OpenAiResponse;
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

    private final OpenAiClient openAiClient;
    private final MessageRepository messageRepository;

    public ChatResponse postMessage(String messageContent, Long userId) {
        List<Message> recentMessages = messageRepository.findTopNByUserIdOrderByCreatedAtDesc(
            userId, 10);
        List<String> context = recentMessages.stream()
            .map(Message::getContent)
            .collect(Collectors.toList());
        Collections.reverse(context);

        OpenAiResponse openAiResponse = openAiClient.getChatResponse(messageContent, context);

        Message userMessage = new Message(userId, Sender.USER, messageContent,
            LocalDateTime.now());
        messageRepository.save(userMessage);

        Message catMessage = new Message(userId, Sender.CAT, openAiResponse.getMessage(),
            LocalDateTime.now());
        messageRepository.save(catMessage);

        return new ChatResponse(openAiResponse.getMessage());
    }

    @Transactional(readOnly = true)
    public Page<MessageResponse> getMessages(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findByUserId(userId, pageable)
            .map(message -> new MessageResponse(message.getId(), message.getContent(), null,
                message.getCreatedAt()));
    }
}
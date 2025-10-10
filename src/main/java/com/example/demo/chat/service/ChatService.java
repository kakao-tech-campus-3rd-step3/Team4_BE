package com.example.demo.chat.service;

import com.example.demo.chat.controller.dto.ChatResponse;
import com.example.demo.chat.controller.dto.MessageResponse;
import com.example.demo.chat.infrastructure.jpa.LongTermMemory;
import com.example.demo.chat.infrastructure.jpa.LongTermMemoryJpaRepository;
import com.example.demo.chat.infrastructure.jpa.Message;
import com.example.demo.chat.infrastructure.jpa.Sender;
import com.example.demo.common.infrastructure.openai.OpenAiClient;
import com.example.demo.common.infrastructure.openai.dto.OpenAiResponse;
import com.example.demo.emotion.domain.DangerState;
import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.service.EmotionRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private final EmotionRepository emotionRepository;
    private final LongTermMemoryJpaRepository longTermMemoryJpaRepository;

    public ChatResponse postMessage(String messageContent, Long userId) {
        List<String> context = fetchContext(userId);
        LongTermMemory longTermMemory = fetchLongTermMemory(userId);
        OpenAiResponse openAiResponse = openAiClient.getChatResponse(messageContent, context, longTermMemory.getMemory());

        Optional<Integer> dangerScore = openAiResponse.getDangerScore();
        Optional<Emotion> emotion = emotionRepository.findById(userId);
        if (dangerScore.isPresent() && emotion.isPresent()) {
            emotion.get().applyDangerLevel(dangerScore.get());
            DangerState state = emotion.get().getDangerState();
            switch (state) {
                case HIGH_DANGER -> longTermMemory.appendHighDangerMessage();
                case BURST -> longTermMemory.appendBurstDangerMessage();
                case CHRONIC -> longTermMemory.appendChronicDangerMessage();
                case STABLE -> longTermMemory.clearDangerMessage();
            }
            emotionRepository.save(emotion.get());
            longTermMemoryJpaRepository.save(longTermMemory);
        }

        Message userMessage = new Message(userId, Sender.USER, messageContent, dangerScore.orElseGet(() -> null),
            LocalDateTime.now());
        messageRepository.save(userMessage);
        Message catMessage = new Message(userId, Sender.CAT, openAiResponse.getMessage(), dangerScore.orElseGet(() -> null),
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

    private List<String> fetchContext(Long userId) {
        List<String> context = messageRepository.findTopNByUserIdOrderByCreatedAtDesc(userId, 10).stream()
            .map(Message::getContent)
            .collect(Collectors.toList());
        Collections.reverse(context);
        return context;
    }

    private LongTermMemory fetchLongTermMemory(Long userId) {
        return longTermMemoryJpaRepository.findById(userId)
            .orElseGet(() -> longTermMemoryJpaRepository.save(new LongTermMemory(userId)));
    }
}
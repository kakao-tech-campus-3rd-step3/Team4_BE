package com.example.demo.chat.service;

import com.example.demo.chat.event.ChatMemoryUpdateEvent;
import com.example.demo.chat.infrastructure.jpa.ChatMemory;
import com.example.demo.chat.infrastructure.jpa.ChatMemoryJpaRepository;
import com.example.demo.openai.OpenAiClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMemoryService {

    private final ChatMemoryJpaRepository chatMemoryRepository;
    private final OpenAiClient openAiClient;
    private final ApplicationEventPublisher publisher;

    public void callUpdate(Long userId, List<String> context) {
        publisher.publishEvent(new ChatMemoryUpdateEvent(userId, context));
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUpdate(ChatMemoryUpdateEvent event) {
        ChatMemory memory = get(event.getUserId());
        String updated = openAiClient.getUpdatedMemory(memory.getMemory(), event.getContext());
        memory.setMemory(updated);
    }

    public ChatMemory get(Long userId) {
        return chatMemoryRepository.findById(userId).orElseGet(() -> {
            try {
                return chatMemoryRepository.saveAndFlush(new ChatMemory(userId));
            } catch (DataIntegrityViolationException e) {
                return chatMemoryRepository.findById(userId)
                    .orElseThrow(() -> new IllegalStateException("Failed to get ChatMemory for user " + userId, e));
            }
        });
    }
}

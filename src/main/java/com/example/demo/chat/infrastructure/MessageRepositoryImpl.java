package com.example.demo.chat.infrastructure;

import com.example.demo.chat.infrastructure.jpa.Message;
import com.example.demo.chat.infrastructure.jpa.MessageJpaRepository;
import com.example.demo.chat.service.MessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {

    private final MessageJpaRepository messageJpaRepository;

    @Override
    public Message save(Message message) {
        return messageJpaRepository.save(message);
    }

    @Override
    public Page<Message> findByUserId(Long userId, Pageable pageable) {
        return messageJpaRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    public List<Message> findTopNByUserIdOrderByCreatedAtDesc(Long userId, int n) {
        Pageable topN = PageRequest.of(0, n);
        return messageJpaRepository.findByUserIdOrderByCreatedAtDesc(userId, topN).getContent();
    }

}

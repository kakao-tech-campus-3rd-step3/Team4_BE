package com.example.demo.chat.service;

import com.example.demo.chat.infrastructure.jpa.Message;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageRepository {

    Message save(Message message);

    Page<Message> findByUserId(Long userId, Pageable pageable);

    List<Message> findTopNByUserIdOrderByCreatedAtDesc(Long userId, int n);

    long countByUserId(Long userId);
}

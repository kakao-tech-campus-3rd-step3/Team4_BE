package com.example.demo.chat.service;

import com.example.demo.chat.domain.Chat;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRepository {

    List<Chat> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);

    Chat save(Chat chat);

    Page<Chat> findByUserId(Long userId, Pageable pageable);

}

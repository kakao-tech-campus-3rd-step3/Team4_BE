package com.example.demo.chat.infrastructure;

import com.example.demo.chat.domain.Chat;
import com.example.demo.chat.infrastructure.jpa.ChatJpaRepository;
import com.example.demo.chat.infrastructure.jpa.Message;
import com.example.demo.chat.service.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository {

    private final ChatJpaRepository chatJpaRepository;

    @Override
    public List<Chat> findTop10ByUserIdOrderByCreatedAtDesc(Long userId) {
        return chatJpaRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(Message::toModel)
                .toList();
    }

    @Override
    public Chat save(Chat chat) {
        return chatJpaRepository.save(Message.fromModel(chat)).toModel();
    }

    @Override
    public Page<Chat> findByUserId(Long userId, Pageable pageable) {
        return chatJpaRepository.findByUserId(userId, pageable).map(Message::toModel);
    }

}

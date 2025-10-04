package com.example.demo.chat.infrastructure.jpa;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends JpaRepository<Message, Long> {

    Page<Message> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    List<Message> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
}
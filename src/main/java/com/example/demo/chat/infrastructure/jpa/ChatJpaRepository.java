package com.example.demo.chat.infrastructure.jpa;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatJpaRepository extends JpaRepository<Message, Long> {

    List<Message> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);

    Page<Message> findByUserId(Long userId, Pageable pageable);

}

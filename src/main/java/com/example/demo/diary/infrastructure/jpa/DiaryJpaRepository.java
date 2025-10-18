package com.example.demo.diary.infrastructure.jpa;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryJpaRepository extends JpaRepository<DiaryEntity, Long> {

    List<DiaryEntity> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}

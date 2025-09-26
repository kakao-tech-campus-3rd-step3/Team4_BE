package com.example.demo.emotion.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionJpaRepository extends JpaRepository<EmotionEntity, Long> {

}

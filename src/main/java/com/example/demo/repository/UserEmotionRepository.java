package com.example.demo.repository;

import com.example.demo.domain.userEmotion.UserEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmotionRepository extends JpaRepository<UserEmotion, Long> {
}

package com.example.demo.repository;

import com.example.demo.domain.emotionTest.EmotionTestResult;
import com.example.demo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionTestResultRepository extends JpaRepository<EmotionTestResult, Long> {

    boolean existsByUser(User user);
}

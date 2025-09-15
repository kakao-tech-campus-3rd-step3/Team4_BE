package com.example.demo.repository;

import com.example.demo.domain.emotionTest.EmotionTestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionTestQuestionRepository extends JpaRepository<EmotionTestQuestion, Long> {

}

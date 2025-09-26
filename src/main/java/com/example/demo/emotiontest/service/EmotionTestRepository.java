package com.example.demo.emotiontest.service;

import com.example.demo.emotiontest.domain.EmotionTest;
import java.util.List;

public interface EmotionTestRepository {

    List<EmotionTest> getAll();
}

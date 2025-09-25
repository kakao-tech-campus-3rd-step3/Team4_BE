package com.example.demo.emotion.service;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.user.domain.User;
import java.util.Optional;

public interface EmotionRepository {

    Emotion save(Emotion emotion);

    Optional<Emotion> findById(Long userId);

    Optional<Emotion> update(Emotion emotion);
}

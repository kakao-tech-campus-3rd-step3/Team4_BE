package com.example.demo.emotion.infrastructure;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.infrastructure.jpa.EmotionEntity;
import com.example.demo.emotion.infrastructure.jpa.EmotionJpaRepository;
import com.example.demo.emotion.service.EmotionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmotionRepositoryImpl implements EmotionRepository {

    private final EmotionJpaRepository jpaRepository;

    @Override
    public Emotion save(Emotion emotion) {
        return jpaRepository.save(EmotionEntity.fromModel(emotion)).toModel();
    }

    @Override
    public Optional<Emotion> findById(Long userId) {
        return jpaRepository.findById(userId).map(EmotionEntity::toModel);
    }

    @Override
    public Optional<Emotion> update(Emotion emotion) {
        return jpaRepository.findById(emotion.getUserId())
            .map(entity -> {
                entity.updateFromModel(emotion);
                return entity.toModel();
            });
    }
}

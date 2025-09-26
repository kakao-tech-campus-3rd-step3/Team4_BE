package com.example.demo.emotiontest.infrastructure;

import com.example.demo.common.util.YamlResourceLoader;
import com.example.demo.emotiontest.domain.EmotionTest;
import com.example.demo.emotiontest.infrastructure.dto.EmotionTestDto;
import com.example.demo.emotiontest.service.EmotionTestRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class EmotionTestRepositoryImpl implements EmotionTestRepository {

    private static final String EMOTION_TEST_PATH = "data/emotion_test.yml";
    private final List<EmotionTest> emotionTest;

    public EmotionTestRepositoryImpl() {
        emotionTest = YamlResourceLoader.loadList(
            EMOTION_TEST_PATH,
                new TypeReference<List<EmotionTestDto>>() {}
            ).stream()
            .sorted(Comparator.comparing(EmotionTestDto::getId))
            .map(EmotionTestDto::toModel)
            .toList();
    }

    @Override
    public List<EmotionTest> getAll() {
        return emotionTest;
    }
}

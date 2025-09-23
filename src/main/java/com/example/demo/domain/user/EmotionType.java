package com.example.demo.domain.user;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum EmotionType {
    SENTIMENT(1), ENERGY(2), COGNITIVE(3), RELATIONSHIP(4), STRESS(5), EMPLOYMENT(6);

    private int questionId;

    EmotionType(int questionId) {
        this.questionId = questionId;
    }

    private static final Map<Integer, EmotionType> BY_ID =
        Arrays.stream(values()).collect(Collectors.toMap(EmotionType::getQuestionId, e -> e));

    public static EmotionType fromId(int id) {
        EmotionType type = BY_ID.get(id);
        if (type == null) {
            throw new IllegalArgumentException("Unknown EmotionType id: " + id);
        }
        return type;
    }
}

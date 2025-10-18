package com.example.demo.emotion.domain;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.errorcode.EmotionTestErrorCode;
import lombok.Getter;

@Getter
public enum EmotionType {
    SENTIMENT(1), ENERGY(2), COGNITIVE(3),
    RELATIONSHIP(4), STRESS(5), EMPLOYMENT(6);
    private long questionId;

    EmotionType(long questionId) {
        this.questionId = questionId;
    }

    public static EmotionType fromQuestionId(long questionId) {
        for (EmotionType type : values()) {
            if (type.questionId == questionId) {
                return type;
            }
        }
        throw new BusinessException(EmotionTestErrorCode.UNDEFINED_QUESTION_ID);
    }
}

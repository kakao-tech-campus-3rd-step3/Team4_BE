package com.example.demo.common.converter;

import com.example.demo.domain.user.EmotionInputVo;
import com.example.demo.domain.user.EmotionType;
import com.example.demo.dto.emotionTest.EmotionTestAnswersRequest;
import java.util.List;

public class EmotionInputConverter {

    public static EmotionInputVo convert(List<EmotionTestAnswersRequest> request) {
        EmotionInputVo converted = new EmotionInputVo();
        for (EmotionTestAnswersRequest r : request) {
            EmotionType type = EmotionType.fromId(r.getQuestionId().intValue());
            Integer value = r.getChoiceIndex() * 30;
            converted.put(type, value);
        }
        return converted;
    }

}

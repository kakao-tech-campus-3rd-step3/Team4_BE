package com.example.demo.mission.regular.infrastructure;

import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.regular.domain.score.MinMaxValue;
import java.util.EnumMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MissionScoreMinMax {

    private final Integer sentimentMin;
    private final Integer sentimentMax;
    private final Integer energyMin;
    private final Integer energyMax;
    private final Integer cognitiveMin;
    private final Integer cognitiveMax;
    private final Integer relationshipMin;
    private final Integer relationshipMax;
    private final Integer stressMin;
    private final Integer stressMax;
    private final Integer employmentMin;
    private final Integer employmentMax;

    public Map<EmotionType, MinMaxValue> toMap() {
        Map<EmotionType, MinMaxValue> map = new EnumMap<>(EmotionType.class);
        map.put(EmotionType.SENTIMENT, new MinMaxValue(sentimentMin, sentimentMax));
        map.put(EmotionType.ENERGY, new MinMaxValue(energyMin, energyMax));
        map.put(EmotionType.COGNITIVE, new MinMaxValue(cognitiveMin, cognitiveMax));
        map.put(EmotionType.RELATIONSHIP, new MinMaxValue(relationshipMin, relationshipMax));
        map.put(EmotionType.STRESS, new MinMaxValue(stressMin, stressMax));
        map.put(EmotionType.EMPLOYMENT, new MinMaxValue(employmentMin, employmentMax));
        return map;
    }
}

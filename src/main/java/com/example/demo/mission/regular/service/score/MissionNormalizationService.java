package com.example.demo.mission.regular.service.score;

import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.regular.domain.MissionScore;
import com.example.demo.mission.regular.service.MissionRepository;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionNormalizationService {

    private final MissionRepository missionRepository;

    @Cacheable(value = "missionMinMax", key = "'ALL'")
    public Map<EmotionType, MinMaxValue> getAllMinMaxData() {

        MissionScoreMinMax missionScoreMinMax = missionRepository.calculateMissionScoreMinMax()
                .orElseThrow(() -> new IllegalStateException("Not Found"));

        return mapResultToCache(missionScoreMinMax);
    }

    public Integer getMin(EmotionType emotionType) {
        return Optional.ofNullable(getAllMinMaxData().get(emotionType))
                .map(MinMaxValue::getMin)
                .orElse(null);
    }

    public Integer getMax(EmotionType emotionType) {
        return Optional.ofNullable(getAllMinMaxData().get(emotionType))
                .map(MinMaxValue::getMax)
                .orElse(null);
    }

    private Map<EmotionType, MinMaxValue> mapResultToCache(MissionScoreMinMax minMax) {
        Map<EmotionType, MinMaxValue> minMaxMap = new EnumMap<>(EmotionType.class);

        minMaxMap.put(EmotionType.SENTIMENT,
                new MinMaxValue(minMax.getSentimentMin(), minMax.getSentimentMax()));
        minMaxMap.put(EmotionType.ENERGY,
                new MinMaxValue(minMax.getEnergyMin(), minMax.getEnergyMax()));
        minMaxMap.put(EmotionType.COGNITIVE,
                new MinMaxValue(minMax.getCognitiveMin(), minMax.getCognitiveMax()));
        minMaxMap.put(EmotionType.RELATIONSHIP,
                new MinMaxValue(minMax.getRelationshipMin(), minMax.getRelationshipMax()));
        minMaxMap.put(EmotionType.SENTIMENT,
                new MinMaxValue(minMax.getSentimentMin(), minMax.getSentimentMax()));
        minMaxMap.put(EmotionType.EMPLOYMENT,
                new MinMaxValue(minMax.getEmploymentMin(), minMax.getEmploymentMax()));
        return minMaxMap;
    }

    public MissionNormalization calculateNormalization(MissionScore missionScore) {
        Integer sentimentNormalization = missionScore.calculateNormalization(
                getMin(EmotionType.SENTIMENT), getMax(EmotionType.SENTIMENT),
                EmotionType.SENTIMENT);
        Integer energyNormalization = missionScore.calculateNormalization(
                getMin(EmotionType.ENERGY), getMax(EmotionType.ENERGY), EmotionType.ENERGY);
        Integer cognitiveNormalization = missionScore.calculateNormalization(
                getMin(EmotionType.COGNITIVE), getMax(EmotionType.COGNITIVE),
                EmotionType.COGNITIVE);
        Integer relationshipNormalization = missionScore.calculateNormalization(
                getMin(EmotionType.RELATIONSHIP), getMax(EmotionType.RELATIONSHIP),
                EmotionType.RELATIONSHIP);
        Integer stressNormalization = missionScore.calculateNormalization(
                getMin(EmotionType.STRESS), getMax(EmotionType.STRESS), EmotionType.STRESS);
        Integer employmentNormalization = missionScore.calculateNormalization(
                getMin(EmotionType.EMPLOYMENT), getMax(EmotionType.EMPLOYMENT),
                EmotionType.EMPLOYMENT);
        return new MissionNormalization(
                sentimentNormalization, energyNormalization, cognitiveNormalization,
                relationshipNormalization, stressNormalization, employmentNormalization
        );
    }

}
package com.example.demo.mission.regular.service;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.emotion.service.EmotionRepository;
import com.example.demo.mission.regular.domain.MissionScore;
import com.example.demo.mission.regular.service.score.MissionScoreMinMax;
import com.example.demo.user.domain.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionEmotionService {
    private final MissionRepository missionRepository;
    private final EmotionRepository emotionRepository;

    public void mission(User user, Long missionId) {
        MissionScore missionScore = missionRepository.findMissionScoreByMissionId(missionId);
        // 정규화
        Integer sentimentNormalization = missionScore.calculateNormalization(MissionScoreMinMax.getMin(EmotionType.SENTIMENT), MissionScoreMinMax.getMax(EmotionType.SENTIMENT), EmotionType.SENTIMENT);
        Integer energyNormalization =  missionScore.calculateNormalization(MissionScoreMinMax.getMin(EmotionType.ENERGY), MissionScoreMinMax.getMax(EmotionType.ENERGY), EmotionType.ENERGY);
        Integer cognitiveNormalization = missionScore.calculateNormalization(MissionScoreMinMax.getMin(EmotionType.COGNITIVE), MissionScoreMinMax.getMax(EmotionType.COGNITIVE), EmotionType.COGNITIVE);
        Integer relationshipNormalization = missionScore.calculateNormalization(MissionScoreMinMax.getMin(EmotionType.RELATIONSHIP), MissionScoreMinMax.getMax(EmotionType.RELATIONSHIP), EmotionType.RELATIONSHIP);
        Integer stressNormalization = missionScore.calculateNormalization(MissionScoreMinMax.getMin(EmotionType.STRESS), MissionScoreMinMax.getMax(EmotionType.STRESS), EmotionType.STRESS);
        Integer employmentNormalization = missionScore.calculateNormalization(MissionScoreMinMax.getMin(EmotionType.EMPLOYMENT), MissionScoreMinMax.getMax(EmotionType.EMPLOYMENT), EmotionType.EMPLOYMENT);

        Optional<Emotion> optionalEmotion = emotionRepository.findById(user.getId());
        Emotion emotion = optionalEmotion.get();
        emotion.updateUserEmotionScore(sentimentNormalization, EmotionType.SENTIMENT);
        emotion.updateUserEmotionScore(energyNormalization, EmotionType.ENERGY);
        emotion.updateUserEmotionScore(cognitiveNormalization, EmotionType.COGNITIVE);
        emotion.updateUserEmotionScore(relationshipNormalization,EmotionType.RELATIONSHIP);
        emotion.updateUserEmotionScore(stressNormalization, EmotionType.STRESS);
        emotion.updateUserEmotionScore(employmentNormalization, EmotionType.EMPLOYMENT);
    }
}

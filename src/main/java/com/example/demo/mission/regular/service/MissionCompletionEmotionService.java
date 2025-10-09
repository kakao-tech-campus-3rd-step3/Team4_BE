package com.example.demo.mission.regular.service;

import com.example.demo.MissionMinMaxCache;
import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.exception.errorcode.EmotionErrorCode;
import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.service.EmotionRepository;
import com.example.demo.mission.regular.domain.score.MissionScores;
import com.example.demo.mission.regular.infrastructure.MissionScoreMinMax;
import com.example.demo.mission.regular.service.score.MissionNormalization;
import com.example.demo.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionCompletionEmotionService {

    private final MissionRepository missionRepository;
    private final EmotionRepository emotionRepository;

    public void updateEmotionOnMissionComplete(User user, Long missionId) {
        MissionScores missionScores = missionRepository.findMissionScoreByMissionId(missionId);
        MissionScoreMinMax missionScoreMinMax = MissionMinMaxCache.getMissionScoreMinMax();

        MissionNormalization normalized = missionScores.normalize(missionScoreMinMax.toMap());

        Emotion emotion = emotionRepository.findById(user.getId())
                .orElseThrow(() -> new BusinessException(EmotionErrorCode.EMOTION_ERROR_CODE));
        emotion.updateAllUserEmotionScores(normalized);
    }
}

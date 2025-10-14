package com.example.demo.emotion.service;

import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.exception.errorcode.EmotionErrorCode;
import com.example.demo.emotion.domain.DangerState;
import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.regular.domain.score.MissionScores;
import com.example.demo.mission.regular.infrastructure.MissionScoreMinMax;
import com.example.demo.mission.regular.service.MissionMinMaxCache;
import com.example.demo.mission.regular.service.MissionRepository;
import com.example.demo.mission.regular.service.score.MissionNormalization;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRepository emotionRepository;
    private final MissionRepository missionRepository;

    public void adjustSentimentEmotion(Integer score, Long userId) {
        if (score.equals(0)) {
            return;
        }

        Emotion emotion = emotionRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(EmotionErrorCode.EMOTION_NOT_FOUND));

        int delta = calculateDelta(emotion.getLevel(EmotionType.SENTIMENT), score);

        emotion.adjust(EmotionType.SENTIMENT, delta);
        emotionRepository.save(emotion);
    }

    public void updateEmotionOnMissionComplete(User user, Long missionId) {
        MissionScores missionScores = missionRepository.findMissionScoreByMissionId(missionId);
        MissionScoreMinMax missionScoreMinMax = MissionMinMaxCache.getMissionScoreMinMax();

        MissionNormalization normalized = missionScores.normalize(missionScoreMinMax.toMap());

        Emotion emotion = emotionRepository.findById(user.getId())
            .orElseThrow(() -> new BusinessException(EmotionErrorCode.EMOTION_NOT_FOUND));

        for (EmotionType value : EmotionType.values()) {
            int delta = calculateDelta(emotion.getLevel(value), normalized.get(value));
            emotion.adjust(value, delta);
        }
        emotionRepository.save(emotion);
    }

    public int calculateDelta(Integer score, Integer input) {
        double a = 0.3;   // 최소 반영 비율 (30%)
        double b = 0.7;   // 감쇠 비율
        double k = 100.0; // 감쇠 강도

        double factor = a + b / Math.sqrt(1 + (double) score / k);
        int delta = (int) Math.round(input * factor);

        if (score + delta < 0) {
            delta = -score;
        }

        return delta;
    }

    public DangerState applyAndGetDangerState(Long userId, int dangerLevel) {
        Emotion emotion = emotionRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(EmotionErrorCode.EMOTION_NOT_FOUND));
        emotion.applyDangerLevel(dangerLevel);
        emotionRepository.save(emotion);
        return emotion.getDangerState();
    }

    public Emotion getEmotion(Long userId) {
        return emotionRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(EmotionErrorCode.EMOTION_NOT_FOUND));
    }

}

package com.example.demo.mission.regular.service;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.service.EmotionRepository;
import com.example.demo.mission.regular.domain.MissionScore;
import com.example.demo.mission.regular.service.score.MissionNormalization;
import com.example.demo.mission.regular.service.score.MissionNormalizationService;
import com.example.demo.user.domain.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionCompletionEmotionService {

    private final MissionRepository missionRepository;
    private final EmotionRepository emotionRepository;
    private final MissionNormalizationService missionNormalizationService;

    public void updateEmotionOnMissionComplete(User user, Long missionId) {
        MissionScore missionScore = missionRepository.findMissionScoreByMissionId(missionId);
        // 정규화
        MissionNormalization missionNormalization = missionNormalizationService.calculateNormalization(
                missionScore);

        Optional<Emotion> optionalEmotion = emotionRepository.findById(user.getId());
        Emotion emotion = optionalEmotion.get();
        emotion.updateAllUserEmotionScores(missionNormalization);
    }
}

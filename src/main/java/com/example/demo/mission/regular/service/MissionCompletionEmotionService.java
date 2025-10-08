package com.example.demo.mission.regular.service;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.service.EmotionRepository;
import com.example.demo.mission.regular.domain.score.MissionScores;
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

    public void updateEmotionOnMissionComplete(User user, Long missionId) {
        MissionScores missionScores = missionRepository.findMissionScoreByMissionId(missionId);
        // 정규화

        missionScores.normalize()

        Optional<Emotion> optionalEmotion = emotionRepository.findById(user.getId());
        Emotion emotion = optionalEmotion.get();
        emotion.updateAllUserEmotionScores(missionNormalization);
    }
}

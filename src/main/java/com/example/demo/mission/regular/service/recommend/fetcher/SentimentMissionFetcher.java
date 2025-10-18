package com.example.demo.mission.regular.service.recommend.fetcher;

import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.domain.RegularMission;
import com.example.demo.mission.regular.service.MissionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SentimentMissionFetcher implements MissionFetcher {

    private final MissionRepository missionRepository;

    @Override
    public List<RegularMission> fetch(MissionCategoryEnum category) {
        return missionRepository.findSentimentMissionsAboveAverageByCategory(category);
    }

    @Override
    public EmotionType getEmotionType() {
        return EmotionType.SENTIMENT;
    }
}

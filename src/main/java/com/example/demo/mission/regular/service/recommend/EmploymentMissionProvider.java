package com.example.demo.mission.regular.service.recommend;

import static com.example.demo.mission.MissionCategoryEnum.EMPLOYMENT;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.mission.regular.domain.RegularMission;
import com.example.demo.mission.regular.infrastructure.MissionScoreMinMax;
import com.example.demo.mission.regular.service.MissionMinMaxCache;
import com.example.demo.mission.regular.service.MissionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmploymentMissionProvider {

    private static final double EFFECTIVE_SCORE_CAP = 200.0;
    private static final Integer RECOMMEND_MAX_SIZE = 4;
    private final MissionRepository missionRepository;

    public List<RegularMission> getMissions(Emotion userEmotion) {
        int userScore = userEmotion.getLevel(EmotionType.EMPLOYMENT);

        MissionScoreMinMax minMax = MissionMinMaxCache.getMissionScoreMinMax();
        if (minMax == null) {
            return missionRepository.findAllByCategory(EMPLOYMENT);
        }
        double min = minMax.getEmploymentMin();
        double max = minMax.getEmploymentMax();
        double range = max - min;
        if (range == 0) {
            return missionRepository.findAllByCategory(EMPLOYMENT);
        }

        double effectiveScore = Math.min(userScore, EFFECTIVE_SCORE_CAP);

        double ratio = effectiveScore == 0 ? 0.0 : (effectiveScore / EFFECTIVE_SCORE_CAP);
        double targetMissionScore = min + (ratio * range);

        int scoreFloor = (int) Math.floor(targetMissionScore);
        int scoreCeil = (int) Math.ceil(targetMissionScore);

        List<RegularMission> employmentList =
                missionRepository.findByCategoryAndEmploymentScoreBetween(EMPLOYMENT, scoreFloor,
                        scoreCeil);

        if (employmentList.size() < RECOMMEND_MAX_SIZE) {
            return missionRepository.findAllByCategory(EMPLOYMENT);
        }

        return employmentList;
    }
}

package com.example.demo.mission.regular.service.recommend;

import static com.example.demo.mission.MissionCategoryEnum.DAILY;
import static com.example.demo.mission.MissionCategoryEnum.EMPLOYMENT;
import static com.example.demo.mission.MissionCategoryEnum.REFRESH;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.emotion.service.EmotionRepository;
import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.EmotionErrorCode;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.controller.dto.MissionCompletionCount;
import com.example.demo.mission.controller.dto.MissionResponse;
import com.example.demo.mission.regular.domain.RegularMission;
import com.example.demo.mission.regular.service.MissionRepository;
import com.example.demo.mission.regular.service.recommend.fetcher.MissionFetcherSelector;
import com.example.demo.plan.service.PlanRepository;
import com.example.demo.user.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionRecommendService {

    private final MissionRepository missionRepository;
    private final PlanRepository planRepository;
    private final EmotionRepository emotionRepository;
    private final MissionFetcherSelector missionFetcherSelector;
    private final EmploymentMissionProvider employmentMissionProvider;

    private static final Integer RECOMMEND_SIZE = 6;
    private static final Map<MissionCategoryEnum, Integer> DEFAULT_RECOMMEND_RATE = Map.of(
            REFRESH, 2, DAILY, 2,
            EMPLOYMENT, 2);

    @Transactional(readOnly = true)
    public List<MissionResponse> getRecommendedMissions(User user) {
        List<MissionCompletionCount> completed = planRepository.findCompletedMissionCount(
                user.getId());

        Map<MissionCategoryEnum, Integer> distribution = calculateDistribution(completed);

        Emotion emotion = emotionRepository.findById(user.getId())
                .orElseThrow(() -> new BusinessException(EmotionErrorCode.EMOTION_NOT_FOUND));
        EmotionType minEmotion = emotion.getMinEmotion();

        List<RegularMission> dailyMissions = getDailyMissions(minEmotion);
        List<RegularMission> refreshMissions = getRefreshMissions(minEmotion);
        List<RegularMission> employmentMissions = employmentMissionProvider.getMissions(emotion);

        Map<MissionCategoryEnum, List<RegularMission>> missionsByCategory = Map.of(
                DAILY, dailyMissions,
                REFRESH, refreshMissions,
                EMPLOYMENT, employmentMissions
        );

        return missionsByCategory.entrySet().stream()
                .flatMap(entry -> selectRandomMissions(entry.getValue(),
                        distribution.get(entry.getKey())).stream())
                .map(MissionResponse::new)
                .toList();
    }

    private Map<MissionCategoryEnum, Integer> calculateDistribution(
            List<MissionCompletionCount> counts) {

        // 전체 수행 횟수 합
        int total = counts.stream().mapToInt(MissionCompletionCount::getCount).sum();

        // 수행 기록이 없는 경우 기본 분포 반환
        if (total == 0) {
            return DEFAULT_RECOMMEND_RATE;
        }

        Map<MissionCategoryEnum, Integer> map = new EnumMap<>(MissionCategoryEnum.class);

        // 1차 분배: 비율에 따라 나누되, 카테고리 별 최소 1개의 미션은 보장
        for (MissionCompletionCount c : counts) {
            int quota = (int) Math.round((c.getCount() / (double) total) * RECOMMEND_SIZE);
            map.put(c.getCategory(), Math.max(1, quota));
        }

        // 합계와 RECOMMEND_SIZE 차이 계산
        int diff = RECOMMEND_SIZE - map.values().stream().mapToInt(Integer::intValue).sum();

        // 2차 분배: 비율에 나눈 1차 분배 내용을 보정
        // RECOMMEND_SIZE에 맞게, 그리고 최소 1개 미션 추천하도록 보장
        if (diff != 0) {
            List<MissionCategoryEnum> categories = new ArrayList<>(map.keySet());
            int index = 0;

            while (diff != 0) {
                MissionCategoryEnum cat = categories.get(index % categories.size());

                if (diff > 0) {
                    map.merge(cat, 1, Integer::sum);
                    diff--;
                } else if (map.get(cat) > 1) {
                    map.merge(cat, -1, Integer::sum);
                    diff++;
                }

                index++;
            }
        }

        return map;
    }

    private List<RegularMission> getDailyMissions(EmotionType minEmotion) {
        return missionFetcherSelector.getFetcher(minEmotion).fetch(DAILY);
    }

    private List<RegularMission> getRefreshMissions(EmotionType minEmotion) {
        return missionFetcherSelector.getFetcher(minEmotion).fetch(REFRESH);
    }


    private List<RegularMission> selectRandomMissions(List<RegularMission> missions, int limit) {
        if (limit == 0) {
            return new ArrayList<>();
        }
        List<RegularMission> shuffled = new ArrayList<>(missions);
        Collections.shuffle(shuffled);

        return shuffled.stream()
                .limit(limit)
                .toList();
    }
}

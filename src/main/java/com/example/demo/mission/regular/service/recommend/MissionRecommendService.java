package com.example.demo.mission.regular.service.recommend;

import static com.example.demo.mission.MissionCategoryEnum.DAILY;
import static com.example.demo.mission.MissionCategoryEnum.EMPLOYMENT;
import static com.example.demo.mission.MissionCategoryEnum.REFRESH;

import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.emotion.service.EmotionRepository;
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

    private static final Integer RECOMMEND_SIZE = 6;
    private static final Map<MissionCategoryEnum, Integer> DEFAULT_RECOMMEND_RATE = Map.of(
        REFRESH, 2, DAILY, 2,
        EMPLOYMENT, 2);

    /**
     * 사용자의 과거 활동, 감정 상태, 카테고리 선호도 데이터를 분석하여 알맞는 미션들을 추천한다.
     *
     * <p>동작 흐름:
     * <ol>
     *   <li>해당 사용자가 완료한 미션 수를 카테고리별로 집계한다.</li>
     *   <li>집계된 데이터로 카테고리별 추천 비율(distribution)을 계산한다.
     *       <ul>
     *         <li>완료한 미션 수가 0이면 DEFAULT_RECOMMEND_RATE을 사용한다.</li>
     *         <li>총합이 6이 되도록 비율을 조정하며, 남는/부족한 수치는 가장 많이 수행된 카테고리에 반영한다.</li>
     *         <li>각 카테고리별로 최소 1개 이상 추천이 보장된다.</li>
     *       </ul>
     *   </li>
     *   <li>사용자의 감정(UserEmotion) 데이터를 조회하여 가장 낮은 감정(minEmotion)을 찾는다.</li>
     *   <li>minEmotion에 따라 DAILY / REFRESH 카테고리에서 평균 이상 점수를 가진 미션들을 조회한다.</li>
     *   <li>EMPLOYMENT 카테고리는 전체 미션을 가져온다.</li>
     *   <li>카테고리별로 분배된 개수만큼 무작위 선택(shuffle 후 limit)하여 최종 추천 목록을 만든다.</li>
     * </ol>
     *
     * @param user the user for whom to generate mission recommendations
     * @return a list of recommended missions wrapped in {@link MissionResponse}
     * @throws RuntimeException if user emotion data cannot be found
     */

    @Transactional(readOnly = true)
    public List<MissionResponse> getRecommendedMissions(User user) {
        List<MissionCompletionCount> completed = planRepository.findCompletedMissionCount(
            user.getId());

        Map<MissionCategoryEnum, Integer> distribution = calculateDistribution(completed);

        Emotion emotion = emotionRepository.findById(user.getId())
            .orElseThrow(() -> new RuntimeException(""));
        EmotionType minEmotion = emotion.getMinEmotion();

        List<RegularMission> dailyMissions = getDailyMissions(minEmotion);
        List<RegularMission> refreshMissions = getRefreshMissions(minEmotion);

        Map<MissionCategoryEnum, List<RegularMission>> missionsByCategory = Map.of(
            DAILY, dailyMissions,
            REFRESH, refreshMissions,
            EMPLOYMENT, missionRepository.findAllByCategory(EMPLOYMENT)
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

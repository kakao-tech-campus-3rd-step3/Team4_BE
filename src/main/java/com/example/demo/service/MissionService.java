package com.example.demo.service;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import com.example.demo.domain.user.User;
import com.example.demo.domain.userEmotion.UserEmotion;
import com.example.demo.domain.userEmotion.UserEmotionTypeEnum;
import com.example.demo.dto.mission.MissionAverageResponse;
import com.example.demo.dto.mission.MissionCountResponse;
import com.example.demo.dto.mission.MissionResponse;
import com.example.demo.repository.MissionRepository;
import com.example.demo.repository.UserEmotionRepository;
import com.example.demo.repository.UserMissionRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserEmotionRepository userEmotionRepository;

    private static final Integer RECOMMEND_SIZE = 6;
    private static final Map<MissionCategoryEnum, Integer> DEFAULT_RECOMMEND_RATE = Map.of(
            MissionCategoryEnum.REFRESH, 2, MissionCategoryEnum.DAILY, 2,
            MissionCategoryEnum.EMPLOYMENT, 2);

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
    public List<MissionResponse> getRecommendedMissions(User user) {
        List<MissionCountResponse> result = userMissionRepository.countByCategory(user.getId());
        Map<MissionCategoryEnum, Integer> countMap = result.stream()
                .collect(Collectors.toMap(MissionCountResponse::getCategory,
                        MissionCountResponse::getCount));
        List<MissionCountResponse> completed = Arrays.stream(MissionCategoryEnum.values())
                .map(category -> new MissionCountResponse(category,
                        countMap.getOrDefault(category, 0)))
                .toList();

        Map<MissionCategoryEnum, Integer> distribution = calculateDistribution(completed);

        UserEmotion emotion = userEmotionRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException(""));
        UserEmotionTypeEnum minEmotion = emotion.getMinEmotion();

        MissionAverageResponse dailyAverage = missionRepository.findAverageScoreByCategory(
                MissionCategoryEnum.DAILY);
        MissionAverageResponse refreshAverage = missionRepository.findAverageScoreByCategory(
                MissionCategoryEnum.REFRESH);

        List<Mission> dailyMissions = getDailyMissions(minEmotion,
                dailyAverage);
        List<Mission> refreshMissions = getRefreshMissions(minEmotion,
                refreshAverage);

        Map<MissionCategoryEnum, List<Mission>> missionsByCategory = Map.of(
                MissionCategoryEnum.DAILY, dailyMissions,
                MissionCategoryEnum.REFRESH, refreshMissions,
                MissionCategoryEnum.EMPLOYMENT,
                missionRepository.findAllByCategory(MissionCategoryEnum.EMPLOYMENT)
        );

        return missionsByCategory.entrySet().stream()
                .flatMap(entry -> selectRandomMissions(entry.getValue(),
                        distribution.get(entry.getKey())).stream())
                .map(MissionResponse::new)
                .toList();
    }

    private Map<MissionCategoryEnum, Integer> calculateDistribution(
            List<MissionCountResponse> counts) {

        int total = counts.stream().mapToInt(MissionCountResponse::getCount).sum();

        if (total == 0) {
            return DEFAULT_RECOMMEND_RATE;
        }

        Map<MissionCategoryEnum, Integer> map = new EnumMap<>(MissionCategoryEnum.class);
        for (MissionCountResponse c : counts) {
            int quota = (int) Math.round((c.getCount() / (double) total) * RECOMMEND_SIZE);
            quota = Math.max(1, quota);
            map.put(c.getCategory(), quota);
        }

        int diff = RECOMMEND_SIZE - map.values().stream().mapToInt(i -> i).sum();
        if (diff != 0) {
            MissionCategoryEnum maxCategory = counts.stream()
                    .max(Comparator.comparingInt(MissionCountResponse::getCount))
                    .map(MissionCountResponse::getCategory)
                    .orElse(MissionCategoryEnum.DAILY);
            map.merge(maxCategory, diff, Integer::sum);
        }

        return map;
    }

    private List<Mission> getDailyMissions(UserEmotionTypeEnum minEmotion,
            MissionAverageResponse dailyAverage) {
        List<Mission> dailyMissions = null;
        switch (minEmotion) {
            case SENTIMENT ->
                    dailyMissions = missionRepository.findByCategoryAndSentimentScoreGreaterThanEqual(
                            MissionCategoryEnum.DAILY, dailyAverage.getSentimentAvgInt());
            case ENERGY ->
                    dailyMissions = missionRepository.findByCategoryAndEnergyScoreGreaterThanEqual(
                            MissionCategoryEnum.DAILY, dailyAverage.getEnergyAvgInt());
            case COGNITIVE ->
                    dailyMissions = missionRepository.findByCategoryAndCognitiveScoreGreaterThanEqual(
                            MissionCategoryEnum.DAILY, dailyAverage.getCognitiveAvgInt());
            case RELATIONSHIP ->
                    dailyMissions = missionRepository.findByCategoryAndRelationshipScoreGreaterThanEqual(
                            MissionCategoryEnum.DAILY, dailyAverage.getRelationshipAvgInt());
            case STRESS ->
                    dailyMissions = missionRepository.findByCategoryAndStressScoreGreaterThanEqual(
                            MissionCategoryEnum.DAILY, dailyAverage.getStressAvgInt());
        }
        return dailyMissions;
    }

    private List<Mission> getRefreshMissions(UserEmotionTypeEnum minEmotion,
            MissionAverageResponse refreshAverage) {
        List<Mission> refreshMissions = null;
        switch (minEmotion) {
            case SENTIMENT ->
                    refreshMissions = missionRepository.findByCategoryAndSentimentScoreGreaterThanEqual(
                            MissionCategoryEnum.REFRESH, refreshAverage.getSentimentAvgInt());
            case ENERGY ->
                    refreshMissions = missionRepository.findByCategoryAndEnergyScoreGreaterThanEqual(
                            MissionCategoryEnum.REFRESH, refreshAverage.getEnergyAvgInt());
            case COGNITIVE ->
                    refreshMissions = missionRepository.findByCategoryAndCognitiveScoreGreaterThanEqual(
                            MissionCategoryEnum.REFRESH, refreshAverage.getCognitiveAvgInt());
            case RELATIONSHIP ->
                    refreshMissions = missionRepository.findByCategoryAndRelationshipScoreGreaterThanEqual(
                            MissionCategoryEnum.REFRESH, refreshAverage.getRelationshipAvgInt());
            case STRESS ->
                    refreshMissions = missionRepository.findByCategoryAndStressScoreGreaterThanEqual(
                            MissionCategoryEnum.REFRESH, refreshAverage.getStressAvgInt());
        }
        return refreshMissions;
    }


    private List<Mission> selectRandomMissions(List<Mission> missions, int limit) {
        if (limit == 0) {
            return new ArrayList<>();
        }
        Collections.shuffle(missions);
        return missions.stream()
                .limit(limit)
                .toList();
    }

}

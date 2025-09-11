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

    private static final Map<MissionCategoryEnum, Integer> defaultRecommendRate = Map.of(
        MissionCategoryEnum.REFRESH, 2, MissionCategoryEnum.DAILY, 2,
        MissionCategoryEnum.EMPLOYMENT, 2);

    public List<MissionResponse> recommend(User user) {
        List<MissionCountResponse> result = userMissionRepository.countByCategory(user.getId());
        Map<MissionCategoryEnum, Integer> countMap = result.stream()
            .collect(Collectors.toMap(MissionCountResponse::getCategory,
                MissionCountResponse::getCount));
        List<MissionCountResponse> completed = Arrays.stream(MissionCategoryEnum.values())
            .map(category -> new MissionCountResponse(category, countMap.getOrDefault(category, 0)))
            .toList();

        Map<MissionCategoryEnum, Integer> distribution = calculateDistribution(completed);

        UserEmotion emotion = userEmotionRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException(""));
        UserEmotionTypeEnum minEmotion = emotion.getMinEmotion();

        MissionAverageResponse dailyAverage = missionRepository.findAverageScoreByCategory(MissionCategoryEnum.DAILY);
        MissionAverageResponse refreshAverage = missionRepository.findAverageScoreByCategory(MissionCategoryEnum.REFRESH);

        List<Mission> dailyMissions = null;
        switch(minEmotion) {
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

        List<Mission> refreshMissions = null;
        switch(minEmotion) {
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

        System.out.println("일상 미션 갯수: " + dailyMissions.size());
        System.out.println("리프레쉬 미션 갯수: " + refreshMissions.size());

        List<MissionResponse> response = new ArrayList<>(
            choose(dailyMissions, distribution.get(MissionCategoryEnum.DAILY)).stream()
                .map(MissionResponse::new)
                .toList());
        response.addAll(
            choose(refreshMissions, distribution.get(MissionCategoryEnum.REFRESH)).stream()
                .map(MissionResponse::new)
                .toList());
        List<Mission> employmentMissions = missionRepository.findAllByCategory(
            MissionCategoryEnum.EMPLOYMENT);
        response.addAll(
            choose(employmentMissions, distribution.get(MissionCategoryEnum.EMPLOYMENT)).stream()
                .map(MissionResponse::new)
                .toList());

        return response;
    }

    private Map<MissionCategoryEnum, Integer> calculateDistribution(
        List<MissionCountResponse> counts) {
        int total = counts.stream().mapToInt(MissionCountResponse::getCount).sum();

        if (total == 0) {
            return defaultRecommendRate;
        }
        Map<MissionCategoryEnum, Integer> map = new EnumMap<>(MissionCategoryEnum.class);
        for (MissionCountResponse c : counts) {
            int quota = (int) Math.round((c.getCount() / (double) total) * 6);
            map.put(c.getCategory(), quota);
        }

        int diff = 6 - map.values().stream().mapToInt(i -> i).sum();
        if (diff != 0) {
            MissionCategoryEnum maxCategory = counts.stream()
                .max(Comparator.comparingInt(MissionCountResponse::getCount))
                .map(MissionCountResponse::getCategory)
                .orElse(MissionCategoryEnum.DAILY);
            map.merge(maxCategory, diff, Integer::sum);
        }
        return map;
    }

    private List<Mission> choose(List<Mission> missions, int limit) {
        if (limit == 0) {
            return new ArrayList<>();
        }
        Collections.shuffle(missions);
        return missions.stream()
                .limit(limit)
                .toList();
    }
}

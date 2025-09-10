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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserEmotionRepository userEmotionRepository;

    public List<MissionResponse> recommend(User user) {
        //미션 목록 6개 반환

        //유저미션 카테고리별로 카운트 쿼리
        List<MissionCountResponse> result = userMissionRepository.countByCategory(user.getId());

        //카운트쿼리가 높은 미션 카테고리를 1순위 추천 대상으로 선정
        // TODO: 갯수 산정 함수
        int[] select = function(result);

        //사용자의 감정 점수 5개를 모두 가져와서
        UserEmotion emotion = userEmotionRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException(""));

        //가장 수치가 작은 감정을 가져옴
        UserEmotionTypeEnum minEmotion = emotion.getMinEmotion();

        MissionAverageResponse dailyAverage = missionRepository.findAverageScoreByCategory(MissionCategoryEnum.DAILY);
        MissionAverageResponse refreshAverage = missionRepository.findAverageScoreByCategory(MissionCategoryEnum.REFRESH);

        Double avg = 0d;
        switch(minEmotion) {
            case SENTIMENT -> avg = dailyAverage.getSentimentAvg();
            case ENERGY -> avg = dailyAverage.getEnergyAvg();
            case COGNITIVE -> avg = dailyAverage.getCognitiveAvg();
            case RELATIONSHIP -> avg = dailyAverage.getRelationshipAvg();
            case STRESS -> avg = dailyAverage.getStressAvg();
        }
        List<Mission> dailyMissions = missionRepository.findMissions(MissionCategoryEnum.DAILY.name(), minEmotion.name(), avg);

        switch(minEmotion) {
            case SENTIMENT -> avg = refreshAverage.getSentimentAvg();
            case ENERGY -> avg = refreshAverage.getEnergyAvg();
            case COGNITIVE -> avg = refreshAverage.getCognitiveAvg();
            case RELATIONSHIP -> avg = refreshAverage.getRelationshipAvg();
            case STRESS -> avg = refreshAverage.getStressAvg();
        }
        List<Mission> refreshMissions = missionRepository.findMissions(MissionCategoryEnum.REFRESH.name(), minEmotion.name(), avg);

        // 추천 결과 생성
        List<MissionResponse> response = new ArrayList<>(choose(dailyMissions, 2).stream()
                .map(MissionResponse::new)
                .toList());
        response.addAll(choose(refreshMissions, 2).stream()
                .map(MissionResponse::new)
                .toList());

        // TODO: 취업 미션에 대한 처리
    }

    private int[] function(List<MissionCountResponse> result) {
        return null;
    }

    private List<Mission> choose(List<Mission> missions, int limit) {
        Collections.shuffle(missions);
        return missions.stream()
                .limit(limit)
                .toList();
    }
}

package com.example.demo;

import com.example.demo.mission.regular.infrastructure.MissionScoreMinMax;
import lombok.Getter;

public class MissionMinMaxCache {

    @Getter
    private static MissionScoreMinMax missionScoreMinMax;

    public static void caching(MissionScoreMinMax minMax) {
        missionScoreMinMax = minMax;
    }

}

package com.example.demo;

import com.example.demo.common.exception.CachingFailedException;
import com.example.demo.mission.regular.infrastructure.MissionScoreMinMax;
import com.example.demo.mission.regular.service.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MissionMinMaxCacheInitializer implements CommandLineRunner {

    private final MissionRepository missionRepository;

    @Override
    public void run(String... args) throws Exception {
        MissionScoreMinMax missionScoreMinMax = missionRepository.calculateMissionScoreMinMax()
                .orElseThrow(CachingFailedException::new);
        MissionMinMaxCache.caching(missionScoreMinMax);
    }
}

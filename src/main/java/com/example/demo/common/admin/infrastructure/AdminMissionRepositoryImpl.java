package com.example.demo.common.admin.infrastructure;

import com.example.demo.common.admin.controller.dto.MissionStatsResponse;
import com.example.demo.common.admin.service.AdminMissionRepository;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionEntity;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionJpaRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminMissionRepositoryImpl implements AdminMissionRepository {

    private final RegularMissionJpaRepository regularMissionJpaRepository;

    @Override
    public List<MissionStatsResponse> findMissionStatistics(MissionCategoryEnum category,
        Sort sort) {
        List<RegularMissionEntity> missions;
        if (category == null) {
            missions = regularMissionJpaRepository.findAll(sort);
        } else {
            missions = regularMissionJpaRepository.findAllByCategory(category, sort);
        }
        return missions.stream()
            .map(MissionStatsResponse::new)
            .collect(Collectors.toList());
    }

}

package com.example.demo.common.admin.infrastructure;

import com.example.demo.common.admin.service.AdminMissionRepository;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionEntity;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminMissionRepositoryImpl implements AdminMissionRepository {

    private final RegularMissionJpaRepository regularMissionJpaRepository;

    @Override
    public List<RegularMissionEntity> findMissionStatistics(MissionCategoryEnum category,
        Sort sort) {
        if (category == null) {
            return regularMissionJpaRepository.findAll(sort);
        }
        return regularMissionJpaRepository.findAllByCategory(category, sort);
    }

}

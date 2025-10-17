package com.example.demo.common.admin.service;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionEntity;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface AdminMissionRepository {

    List<RegularMissionEntity> findMissionStatistics(MissionCategoryEnum category, Sort sort);

}

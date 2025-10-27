package com.example.demo.admin.service;

import com.example.demo.admin.controller.dto.MissionStatsResponse;
import com.example.demo.mission.MissionCategoryEnum;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface AdminMissionRepository {

    List<MissionStatsResponse> findMissionStatistics(MissionCategoryEnum category, Sort sort);

}

package com.example.demo.repository;

import com.example.demo.domain.mission.CustomMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomMissionRepository extends JpaRepository<CustomMission, Long> {

}

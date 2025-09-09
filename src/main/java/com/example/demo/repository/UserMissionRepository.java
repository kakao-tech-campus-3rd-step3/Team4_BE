package com.example.demo.repository;

import com.example.demo.domain.mission.UserMission;
import com.example.demo.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    List<UserMission> findByUser(User user);
}

package com.example.demo.service;

import com.example.demo.domain.mission.UserMission;
import com.example.demo.domain.user.User;
import com.example.demo.dto.plan.PlanResponse;
import com.example.demo.repository.UserMissionRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanQueryService {

    private final UserMissionRepository userMissionRepository;

    public List<PlanResponse> getPlans(LocalDate date, Boolean isDone, User user) {
        List<UserMission> userMissions = userMissionRepository.findByUserAndDateAndDone(
            user,
            date.atStartOfDay(),
            date.atTime(LocalTime.MAX),
            isDone
        );

        return userMissions.stream()
            .map(PlanResponse::new)
            .collect(Collectors.toList());
    }
}
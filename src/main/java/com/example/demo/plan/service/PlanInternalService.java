package com.example.demo.plan.service;

import com.example.demo.plan.controller.dto.PlanCreateRequest;
import com.example.demo.plan.domain.Plan;
import com.example.demo.user.domain.User;

public interface PlanInternalService {

    Long addMissionToPlan(PlanCreateRequest request, User user);

    Plan updatePlanStatus(Long planId, boolean isDone, User user);

}

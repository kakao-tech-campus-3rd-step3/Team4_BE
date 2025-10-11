package com.example.demo.plan.service;

import com.example.demo.plan.controller.dto.TodayPlansResponse;
import com.example.demo.user.domain.User;

public interface PlanService {

    void deletePlan(Long planId, User user);

    TodayPlansResponse getTodayPlans(User user);

}

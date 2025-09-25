package com.example.demo.plan.infrastructure;

import com.example.demo.plan.domain.Plan;
import com.example.demo.plan.domain.TodayPlans;
import com.example.demo.plan.infrastructure.jpa.PlanEntity;
import com.example.demo.plan.infrastructure.jpa.PlanJpaRepository;
import com.example.demo.plan.service.PlanRepository;
import com.example.demo.user.domain.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanJpaRepository planJpaRepository;

    @Override
    public TodayPlans findTodayPlans(User user) {
        LocalDate today = LocalDate.now();
        return new TodayPlans(
            user.getId(),
            planJpaRepository.findByUserIdAndCreatedAtBetween(
                    user.getId(),
                    today.atStartOfDay(),
                    today.atTime(LocalTime.MAX)).stream().map(PlanEntity::toModel)
                .toList());
    }

    @Override
    public void saveAll(List<Plan> plans) {
        planJpaRepository.saveAll(plans.stream().map(PlanEntity::fromModel).toList());
    }

    @Override
    public Plan save(Plan plan) {
        return planJpaRepository.save(PlanEntity.fromModel(plan)).toModel();
    }

    @Override
    public Optional<Plan> findById(Long planId) {
        return planJpaRepository.findById(planId).map(PlanEntity::toModel);
    }
}

package com.example.demo.common.admin.service;

import com.example.demo.common.admin.controller.dto.CreateProductItemRequest;
import com.example.demo.common.admin.controller.dto.UpdateMissionPromotion;
import com.example.demo.common.admin.domain.MissionPromotion;
import com.example.demo.common.config.MissionMinMaxCacheInitializer;
import com.example.demo.mission.controller.dto.ScoreEvaluateResponse;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import com.example.demo.mission.custom.service.ScoreEvaluationService;
import com.example.demo.mission.regular.service.MissionRepository;
import com.example.demo.product.domain.DisplayImage;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.product.service.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final ProductItemRepository productItemRepository;
    private final MissionPromotionRepository missionPromotionRepository;
    private final ScoreEvaluationService scoreEvaluationService;
    private final MissionRepository missionRepository;
    private final MissionMinMaxCacheInitializer cacheInitializer;

    private static final Integer PAGE_SIZE = 10;

    public void registerItem(CreateProductItemRequest request) {
        ProductItem product = new ProductItem(
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                new DisplayImage(request.getImageUrl(), request.getOffsetX(), request.getOffsetY())
        );

        productItemRepository.save(product);
    }

    public Page<MissionPromotion> listPromotions(int page, CustomMissionStateEnum state) {
        return missionPromotionRepository.findAllByState(PageRequest.of(page, PAGE_SIZE),
            state);
    }

    public void evaluate(Long promotionId) {
        MissionPromotion missionPromotion = getPromotionById(promotionId);

        if (missionPromotion.getState() != CustomMissionStateEnum.WAITING) {
            throw new RuntimeException("WAITING 상태의 프로모션만 필터링할 수 있습니다");
        }

        ScoreEvaluateResponse response = scoreEvaluationService.evaluateScore(
            missionPromotion.getContent());
        missionPromotion.filtered(response.getSentimentScore(), response.getEnergyScore(),
            response.getCognitiveScore(), response.getRelationshipScore(),
            response.getStressScore(),
            response.getEmploymentScore(), response.getLevel());

        missionPromotionRepository.save(missionPromotion);
    }

    public void promoteMission(Long promotionId) {
        MissionPromotion missionPromotion = getPromotionById(promotionId);

        if (missionPromotion.getState() != CustomMissionStateEnum.FILTERED) {
            throw new RuntimeException("FILTERED 상태의 미션만 승격할 수 있습니다");
        }

        missionPromotion.promoted();
        missionPromotionRepository.save(missionPromotion);
        missionRepository.saveAsRegularMission(missionPromotion);

        cacheInitializer.initializeCache();
    }

    public void rejectMission(Long promotionId) {
        MissionPromotion missionPromotion = getPromotionById(promotionId);

        missionPromotion.rejected();
        missionPromotionRepository.save(missionPromotion);
    }

    public MissionPromotion getPromotion(Long id) {
        return getPromotionById(id);
    }

    public void updatePromotion(Long promotionId, UpdateMissionPromotion update) {
        MissionPromotion missionPromotion = getPromotionById(promotionId);
        missionPromotion.update(
            update.getContent(),
            update.getCategory(),
            update.getSentimentScore(),
            update.getEnergyScore(),
            update.getCognitiveScore(),
            update.getRelationshipScore(),
            update.getStressScore(),
            update.getEmploymentScore(),
            update.getLevel()
        );
        missionPromotionRepository.save(missionPromotion);
    }

    private MissionPromotion getPromotionById(Long promotionId) {
        return missionPromotionRepository.findById(promotionId)
            .orElseThrow(RuntimeException::new);
    }
}

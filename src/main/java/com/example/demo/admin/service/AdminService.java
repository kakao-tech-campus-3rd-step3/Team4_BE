package com.example.demo.admin.service;

import com.example.demo.admin.controller.dto.MissionStatsResponse;
import com.example.demo.admin.controller.dto.UpdateMissionPromotion;
import com.example.demo.admin.controller.dto.CreateProductItemRequest;
import com.example.demo.admin.domain.MissionPromotion;
import com.example.demo.config.MissionMinMaxCacheInitializer;
import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.AdminErrorCode;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.controller.dto.ScoreEvaluateResponse;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import com.example.demo.mission.custom.service.ScoreEvaluationService;
import com.example.demo.mission.regular.service.MissionRepository;
import com.example.demo.product.domain.DisplayImage;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.product.service.ProductItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final AdminMissionRepository adminMissionRepository;

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
            throw new BusinessException(AdminErrorCode.INVALID_FILTER_REQUEST);
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
            throw new BusinessException(AdminErrorCode.INVALID_PROMOTE_REQUEST);
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

    @Transactional(readOnly = true)
    public List<ProductItem> listAllProducts() {
        return productItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductItem getProductById(Long id) {
        return productItemRepository.findById(id)
            .orElseThrow(() -> new BusinessException(AdminErrorCode.PRODUCT_NOT_FOUND));
    }

    public void updateProduct(Long id, CreateProductItemRequest request) {
        ProductItem existingProduct = getProductById(id);
        ProductItem updatedProduct = new ProductItem(
            id,
            request.getName(),
            request.getPrice(),
            request.getCategory(),
            new DisplayImage(request.getImageUrl(), request.getOffsetX(), request.getOffsetY())
        );
        productItemRepository.save(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productItemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MissionStatsResponse> getMissionStats(MissionCategoryEnum category, String sortBy,
        String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder),
            "missionCountEmbeddable." + sortBy);
        List<MissionStatsResponse> stats = adminMissionRepository.findMissionStatistics(category,
            sort);
        return stats;
    }
}

package com.example.demo.common.admin.service;

import com.example.demo.common.admin.controller.dto.CreateProductItemRequest;
import com.example.demo.common.admin.controller.dto.MissionStatsResponse;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.product.domain.DisplayImage;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.product.service.ProductItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final ProductItemRepository productItemRepository;
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

    @Transactional(readOnly = true)
    public List<ProductItem> listAllProducts() {
        return productItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductItem getProductById(Long id) {
        return productItemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다: " + id));
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

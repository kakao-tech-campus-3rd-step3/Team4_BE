package com.example.demo.common.admin.service;

import com.example.demo.common.admin.controller.dto.CreateProductItemRequest;
import com.example.demo.product.domain.DisplayImage;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.product.service.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final ProductItemRepository productItemRepository;

    public void registerItem(CreateProductItemRequest request) {
        ProductItem product = new ProductItem(
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                new DisplayImage(request.getImageUrl(), request.getOffsetX(), request.getOffsetY())
        );

        productItemRepository.save(product);
    }

}

package com.example.demo.cat.service;

import com.example.demo.cat.controller.dto.EquipItemRequest;
import com.example.demo.cat.domain.Cat;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.product.infrastructure.ProductItemJpaRepository;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemCommandService {

    private final ProductItemJpaRepository productRepository;
    private final CatRepository catRepository;

    public void purchaseItem(Long productId, User user) {
        Cat cat = getCatById(user.getId());
        ProductItem product = getProductById(productId);
        cat.purchaseItem(product);
    }

    public void setItemEquipped(EquipItemRequest request, Long itemId, User user) {
        Cat cat = getCatById(user.getId());

        if (request.getIsUsed()) {
            cat.equip(itemId);
        } else {
            cat.unEquip(itemId);
        }
    }

    private Cat getCatById(Long id) {
        return catRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("고양이를 찾는데 실패하였습니다."));
    }

    private ProductItem getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("아이템을 찾는데 실패하였습니다.")).toDomain();
    }
}

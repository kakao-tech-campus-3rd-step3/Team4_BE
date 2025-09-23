package com.example.demo.product.infrastructure;

import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.cat.domain.Item;
import com.example.demo.product.controller.dto.ProductItemResponse;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.product.infrastructure.jpa.ProductItemEntity;
import com.example.demo.product.service.ProductItemRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductItemRepositoryImpl implements ProductItemRepository {

    private final ProductItemJpaRepository productItemJpaRepository;

    @Override
    public Page<ProductItemResponse> findAllByCategoryOrderByIdAsc(Pageable pageable,
            EquipSlot category, Cat cat) {
        Page<ProductItem> products = productItemJpaRepository.findAllByCategoryOrderById(pageable,
                category).map(
                ProductItemEntity::toDomain);

        Set<Long> ids = cat.getItems().stream().map(Item::getProductId).collect(Collectors.toSet());

        return products.map(p -> new ProductItemResponse(p, ids.contains(p.getId())));
    }
}

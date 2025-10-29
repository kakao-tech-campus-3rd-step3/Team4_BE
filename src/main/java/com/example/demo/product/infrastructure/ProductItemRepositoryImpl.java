package com.example.demo.product.infrastructure;

import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.cat.domain.Item;
import com.example.demo.product.controller.dto.ProductItemResponse;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.product.infrastructure.jpa.DisplayImageEmbeddable;
import com.example.demo.product.infrastructure.jpa.ProductItemEntity;
import com.example.demo.product.service.ProductItemRepository;
import java.util.List;
import java.util.Optional;
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
            ProductItemEntity::toModel);

        Set<Long> ids = cat.getItems().stream().map(Item::getProductId).collect(Collectors.toSet());

        return products.map(p -> new ProductItemResponse(p, ids.contains(p.getId())));
    }

    @Override
    public Optional<ProductItem> findById(Long id) {
        return productItemJpaRepository.findById(id).map(ProductItemEntity::toModel);
    }

    @Override
    public List<ProductItem> findAll() {
        return productItemJpaRepository.findAll().stream()
            .map(ProductItemEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        productItemJpaRepository.deleteById(id);
    }

    @Override
    public ProductItem save(ProductItem productItem) {
        ProductItemEntity entity = new ProductItemEntity(
            productItem.getId(),
            productItem.getName(),
            productItem.getPrice(),
            productItem.getSlot(),
            new DisplayImageEmbeddable(
                productItem.getImage().getImageUrl(),
                productItem.getImage().getOffsetX(),
                productItem.getImage().getOffsetY()
            )
        );
        return productItemJpaRepository.save(entity).toModel();
    }
}

package com.example.demo.product.service;

import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.product.controller.dto.ProductItemResponse;
import com.example.demo.product.domain.ProductItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductItemRepository {

    Page<ProductItemResponse> findAllByCategoryOrderByIdAsc(Pageable pageable, EquipSlot category,
        Cat cat);

    Optional<ProductItem> findById(Long id);

    List<ProductItem> findAll();

    void deleteById(Long id);

    ProductItem save(ProductItem product);
}

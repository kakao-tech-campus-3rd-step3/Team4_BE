package com.example.demo.product.infrastructure;

import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.product.controller.dto.ProductItemResponse;
import com.example.demo.product.infrastructure.jpa.ProductItemEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductItemJpaRepository extends JpaRepository<ProductItemEntity, Long> {

    Page<ProductItemEntity> findAllByCategoryOrderById(Pageable pageable, EquipSlot category);
}

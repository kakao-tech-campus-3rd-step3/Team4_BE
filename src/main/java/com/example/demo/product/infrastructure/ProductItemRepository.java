package com.example.demo.product.infrastructure;

import com.example.demo.product.infrastructure.jpa.ProductItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItemEntity, Long> {

}

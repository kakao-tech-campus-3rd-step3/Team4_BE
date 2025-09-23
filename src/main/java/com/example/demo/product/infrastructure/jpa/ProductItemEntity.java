package com.example.demo.product.infrastructure.jpa;

import com.example.demo.cat.infrastructure.jpa.DisplayImage;
import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.product.domain.ProductItem;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
//admin만 수정 가능
public class ProductItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EquipSlot category;

    @Embedded
    private DisplayImage displayImage;

    public ProductItem toDomain() {
        return new ProductItem(id, name, price, category, displayImage);
    }
}

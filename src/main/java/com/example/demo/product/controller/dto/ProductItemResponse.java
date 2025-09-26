package com.example.demo.product.controller.dto;

import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.product.domain.ProductItem;
import lombok.Getter;

@Getter
public class ProductItemResponse {

    private final Long id;
    private final EquipSlot category;
    private final String name;
    private final Integer price;
    private final String imageUrl;
    private final Boolean isOwned;

    public ProductItemResponse(ProductItem productItem, Boolean isOwned) {
        this.id = productItem.getId();
        this.category = productItem.getSlot();
        this.name = productItem.getName();
        this.price = productItem.getPrice();
        this.imageUrl = productItem.getImage().getImageUrl();
        this.isOwned = isOwned;
    }
}

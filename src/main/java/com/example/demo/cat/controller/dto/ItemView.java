package com.example.demo.cat.controller.dto;

import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.product.domain.DisplayImage;
import lombok.Getter;

@Getter
public class ItemView {

    private final Long id;
    private final Long productId;
    private final EquipSlot slot;
    private final Integer price;
    private final String name;
    private final DisplayImage image;
    private final Boolean isUsed;

    public ItemView(Long id, Long productId, EquipSlot slot, Integer price, String name,
        String imageUrl, Float offsetX, Float offsetY,
        Boolean isUsed) {

        this.id = id;
        this.productId = productId;
        this.slot = slot;
        this.price = price;
        this.name = name;
        this.image = new DisplayImage(imageUrl, offsetX, offsetY);
        this.isUsed = isUsed;
    }
}

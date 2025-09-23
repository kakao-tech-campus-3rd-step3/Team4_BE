package com.example.demo.cat.controller.dto;

import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.cat.domain.Item;
import lombok.Getter;

@Getter
public class ItemResponse {
    private final Long id;
    private final EquipSlot category;
    private final String name;
    private final String imageUrl;
    private final Float offsetX;
    private final Float offsetY;
    private final Boolean isUsed;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.category = item.getSlot();
        this.name = item.getName();
        this.imageUrl = item.get();
        this.offsetX = item.getOffsetX();
        this.offsetY = item.getOffsetY();
        this.isUsed = item.getIsUsed();
    }
}

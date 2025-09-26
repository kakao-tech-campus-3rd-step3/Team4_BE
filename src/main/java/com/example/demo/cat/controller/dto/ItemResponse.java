package com.example.demo.cat.controller.dto;

import com.example.demo.cat.domain.EquipSlot;
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

    public ItemResponse(Long id, EquipSlot category, String name, String imageUrl, Float offsetX,
        Float offsetY, Boolean isUsed) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.imageUrl = imageUrl;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.isUsed = isUsed;
    }
}

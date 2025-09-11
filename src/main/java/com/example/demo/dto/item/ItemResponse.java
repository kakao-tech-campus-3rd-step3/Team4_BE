package com.example.demo.dto.item;

import com.example.demo.domain.cat.Item;
import lombok.Getter;

@Getter
public class ItemResponse {

    private String imageUrl;
    private Float offsetX;
    private Float offsetY;

    public ItemResponse(Item item) {
        imageUrl = item.getImageUrl();
        offsetX = item.getOffsetX();
        offsetY = item.getOffsetY();
    }
}

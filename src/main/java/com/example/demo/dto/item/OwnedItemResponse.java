package com.example.demo.dto.item;

import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.domain.cat.OwnedItem;
import lombok.Getter;

@Getter
public class OwnedItemResponse {
    private final Long id;
    private final ItemCategoryEnum category;
    private final String name;
    private final String imageUrl;
    private final Float offsetX;
    private final Float offsetY;
    private final Boolean isUsed;

    public OwnedItemResponse(OwnedItem ownedItem) {
        Item item = ownedItem.getItem();
        this.id = ownedItem.getId();
        this.category = item.getCategory();
        this.name = item.getName();
        this.imageUrl = item.getImageUrl();
        this.offsetX = item.getOffsetX();
        this.offsetY = item.getOffsetY();
        this.isUsed = ownedItem.getIsUsed();
    }
}

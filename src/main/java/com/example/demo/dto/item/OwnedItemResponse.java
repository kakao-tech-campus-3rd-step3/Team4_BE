package com.example.demo.dto.item;

import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.domain.cat.OwnedItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnedItemResponse {
    private Long id;
    private ItemCategoryEnum category;
    private String name;
    private String imageUrl;
    private Float offsetX;
    private Float offsetY;
    private Boolean isUsed;

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

package com.example.demo.dto.item;

import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.ItemCategoryEnum;
import lombok.Getter;

@Getter
public class ShopItemResponse {

    private final Long id;
    private final ItemCategoryEnum category;
    private final String name;
    private final Integer price;
    private final String imageUrl;
    private final Boolean isOwned;

    public ShopItemResponse(Item item, Boolean isOwned) {
        this.id = item.getId();
        this.category = item.getCategory();
        this.name = item.getName();
        this.price = item.getPrice();
        this.imageUrl = item.getImageUrl();
        this.isOwned = isOwned;
    }
}

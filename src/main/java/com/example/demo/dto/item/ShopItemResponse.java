package com.example.demo.dto.item;

import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.ItemCategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopItemResponse {

    private Long id;
    private ItemCategoryEnum category;
    private String name;
    private Integer price;
    private String imageUrl;
    private Boolean isOwned;

    public ShopItemResponse(Item item, Boolean isOwned) {
        this.id = item.getId();
        this.category = item.getCategory();
        this.name = item.getName();
        this.price = item.getPrice();
        this.imageUrl = item.getImageUrl();
        this.isOwned = isOwned;
    }
}

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

    public ShopItemResponse(Item item) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isOwned = isOwned;
    }
}

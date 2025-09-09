package com.example.demo.dto.item;

import com.example.demo.domain.cat.ItemCategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateItemRequest {

    private String name;
    private Integer price;
    private ItemCategoryEnum category;
    private String imageUrl;
    private Float offsetX;
    private Float offsetY;
}

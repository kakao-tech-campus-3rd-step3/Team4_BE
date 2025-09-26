package com.example.demo.common.admin.controller.dto;

import com.example.demo.cat.domain.EquipSlot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductItemRequest {

    private String name;
    private Integer price;
    private EquipSlot category;
    private String imageUrl;
    private Float offsetX;
    private Float offsetY;
}

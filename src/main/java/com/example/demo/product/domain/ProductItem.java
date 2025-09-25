package com.example.demo.product.domain;

import com.example.demo.cat.domain.EquipSlot;
import lombok.Getter;

@Getter
public class ProductItem {
    private Long id;
    private String name;
    private Integer price;
    private EquipSlot slot;
    private DisplayImage image;

    public ProductItem(Long id, String name, Integer price, EquipSlot slot, DisplayImage image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.slot = slot;
        this.image = image;
    }

    public ProductItem(String name, Integer price, EquipSlot slot, DisplayImage image) {
        this(null, name, price, slot, image);
    }
}

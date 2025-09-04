package com.example.demo.domain.cat;

import com.example.demo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "item")
@Getter
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ItemCategoryEnum category;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Float offsetX;

    @Column(nullable = false)
    private Float offsetY;

    protected Item() {
    }

    private Item(String name, Integer price, ItemCategoryEnum category, String imageUrl, Float offsetX, Float offsetY) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public static Item of(String name, Integer price, ItemCategoryEnum category, String imageUrl, Float offsetX, Float offsetY) {
        return new Item(name, price, category, imageUrl, offsetX, offsetY);
    }

}
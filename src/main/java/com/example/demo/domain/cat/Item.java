package com.example.demo.domain.cat;

import com.example.demo.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "item")
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

    protected Item() {
    }

    private Item(Integer price, ItemCategoryEnum category, String imageUrl) {
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public static Item of(Integer price, ItemCategoryEnum category, String imageUrl) {
        return new Item(price, category, imageUrl);
    }

    public void updateDetail(Integer price, ItemCategoryEnum category) {
        this.price = price;
        this.category = category;
    }

}

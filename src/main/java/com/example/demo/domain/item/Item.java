package com.example.demo.domain.item;

import com.example.demo.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
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

    public static Item of(String imageUrl) {
        return new Item(null, null, imageUrl);
    }

    public void updateDetail(Integer price, ItemCategoryEnum category) {
        this.price = price;
        this.category = category;
    }


}

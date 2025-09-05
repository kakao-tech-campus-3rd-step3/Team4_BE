package com.example.demo.domain.cat;

import com.example.demo.domain.common.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
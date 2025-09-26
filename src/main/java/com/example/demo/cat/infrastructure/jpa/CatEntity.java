package com.example.demo.cat.infrastructure.jpa;

import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.domain.EquippedItems;
import com.example.demo.cat.domain.Item;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class CatEntity {

    @Id
    private Long userId;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> itemEntities = new ArrayList<>();

    protected CatEntity() {
    }

    private CatEntity(Long userId, String name, List<ItemEntity> itemEntities) {
        this.userId = userId;
        this.name = name;
        this.itemEntities = itemEntities;
    }

    public Cat toModel() {
        List<Item> items = itemEntities.stream().map(ItemEntity::toModel).toList();
        return new Cat(userId, name, items, new EquippedItems(items));
    }

    public static CatEntity fromModel(Cat cat) {
        return new CatEntity(cat.getUserId(), cat.getName(),
            cat.getItems().stream().map(ItemEntity::fromModel).toList());
    }

}
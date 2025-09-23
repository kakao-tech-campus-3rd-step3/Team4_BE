package com.example.demo.cat.infrastructure.jpa;

import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.domain.EquippedItems;
import com.example.demo.cat.domain.Item;
import com.example.demo.user.infrastructure.jpa.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Entity
@Getter
public class CatEntity {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> itemEntities = new ArrayList<>();

    protected CatEntity() {
    }

    private CatEntity(Long id, UserEntity userEntity, String name, List<ItemEntity> itemEntities) {
        this.id = id;
        this.userEntity = userEntity;
        this.name = name;
        this.itemEntities = itemEntities;
    }

    public Cat toModel() {
        List<Item> items = itemEntities.stream().map(ItemEntity::toModel).toList();
        return new Cat(id, userEntity.toModel(), name, items, new EquippedItems(items));
    }

    public static CatEntity fromModel(Cat cat) {
        return new CatEntity(cat.getId(), UserEntity.fromModel(cat.getOwner()), cat.getName(),
            cat.getItems().stream().map(ItemEntity::fromModel).toList());
    }

}
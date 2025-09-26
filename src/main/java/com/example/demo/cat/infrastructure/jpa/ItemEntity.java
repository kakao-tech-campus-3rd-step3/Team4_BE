package com.example.demo.cat.infrastructure.jpa;

import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.cat.domain.Item;
import com.example.demo.common.infrastructure.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "item")
@Getter
public class ItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipSlot slot;

    @Column(nullable = false)
    private boolean equipped;

    protected ItemEntity() {
    }

    private ItemEntity(Long id, Long productId, EquipSlot slot, boolean equipped) {
        this.id = id;
        this.productId = productId;
        this.slot = slot;
        this.equipped = equipped;
    }

    public static ItemEntity fromModel(Item item) {
        return new ItemEntity(item.getId(), item.getProductId(), item.getSlot(), item.isEquipped());
    }

    public Item toModel() {
        return new Item(id, productId, slot, equipped);
    }
}
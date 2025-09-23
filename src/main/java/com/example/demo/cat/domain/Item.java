package com.example.demo.cat.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Item {
    private Long id;
    private Long productId;
    private String name;
    private EquipSlot slot;
    private boolean equipped;

    public Item(Long id, Long productId, String name, EquipSlot slot, boolean equipped) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.slot = slot;
        this.equipped = equipped;
    }

    public Item(Long productId,  String name, EquipSlot slot, boolean equipped) {
        this(null, productId, name, slot, equipped);
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

    public void equip(boolean equipped) {
        this.equipped = equipped;
    }
}

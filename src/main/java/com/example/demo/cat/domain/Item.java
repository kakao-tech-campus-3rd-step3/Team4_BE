package com.example.demo.cat.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Item {

    private final Long id;
    private final Long productId;
    private final EquipSlot slot;
    private boolean equipped;

    public Item(Long id, Long productId, EquipSlot slot, boolean equipped) {
        this.id = id;
        this.productId = productId;
        this.slot = slot;
        this.equipped = equipped;
    }

    public Item(Long productId, EquipSlot slot, boolean equipped) {
        this(null, productId, slot, equipped);
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

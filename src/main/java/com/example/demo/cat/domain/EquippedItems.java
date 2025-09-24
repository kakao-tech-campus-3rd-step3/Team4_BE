package com.example.demo.cat.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EquippedItems {

    private Map<EquipSlot, Item> equippedItems;

    public EquippedItems(List<Item> items) {
        this.equippedItems = items.stream().filter(Item::isEquipped)
            .collect(Collectors.toMap(Item::getSlot, Function.identity()));
    }

    public EquippedItems() {
        this.equippedItems = new HashMap<>();
    }

    public void equip(Item item) {
        Item remove = equippedItems.remove(item.getSlot());
        if (remove != null) {
            remove.equip(false);
        }
        item.equip(true);
        equippedItems.put(item.getSlot(), item);
    }

    public void unEquip(Item item) {
        Item remove = equippedItems.remove(item.getSlot());
        remove.equip(false);
    }

    public List<Item> getEquippedItems() {
        return new ArrayList<>(equippedItems.values());
    }
}

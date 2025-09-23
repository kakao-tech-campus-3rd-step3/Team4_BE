package com.example.demo.cat.domain;

import com.example.demo.product.domain.ProductItem;
import com.example.demo.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Cat {

    private Long id;
    private User owner;
    private String name;
    private List<Item> items;
    private EquippedItems equippedItems;

    public Cat(Long id, User owner, String name, List<Item> items, EquippedItems equippedItems) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.items = items;
        this.equippedItems = equippedItems;
    }

    public Cat(User owner, String name) {
        this(null, owner, name, new ArrayList<>(), new EquippedItems());
    }

    public void purchaseItem(ProductItem productItem) {
        Item item = new Item(productItem.getId(), productItem.getSlot(), false);
        items.add(item);
    }

    public void equip(Long itemId) {
        Item item = getItem(itemId);
        equippedItems.equip(item);
    }

    public void unEquip(Long itemId) {
        Item item = getItem(itemId);
        equippedItems.unEquip(item);
    }

    public void rename(String catName) {
        this.name = catName;
    }

    private Item getItem(Long itemId) {
        return items.stream().filter(i -> i.getId().equals(itemId)).findAny()
            .orElseThrow(() -> new RuntimeException("아이템을 소유하고 있지 않습니다."));
    }
}

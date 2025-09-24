package com.example.demo.cat.domain;

import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.exception.errorcode.ItemErrorCode;
import com.example.demo.product.domain.ProductItem;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Cat {

    private final Long userId;
    private String name;
    private List<Item> items;
    private EquippedItems equippedItems;

    public Cat(Long userId, String name, List<Item> items, EquippedItems equippedItems) {
        this.userId = userId;
        this.name = name;
        this.items = items;
        this.equippedItems = equippedItems;
    }

    public Cat(Long userId, String name) {
        this(userId, name, new ArrayList<>(), new EquippedItems());
    }

    public void purchaseItem(ProductItem productItem) {
        validateItemExist(productItem);
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
            .orElseThrow(() -> new BusinessException(ItemErrorCode.ITEM_NOT_EXIST));
    }

    private void validateItemExist(ProductItem productItem) {
        Long id = productItem.getId();
        if (items.stream().anyMatch(i -> i.getProductId().equals(id))) {
            throw new BusinessException(ItemErrorCode.ITEM_ALREADY_EXIST);
        }
    }
}

package com.example.demo.domain.cat;

import com.example.demo.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.*;

@Entity
@Getter
public class Cat {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedItem> ownedItems = new ArrayList<>();

    @Transient
    private Map<ItemCategoryEnum, OwnedItem> equippedItem = new HashMap<>();

    protected Cat() {
    }

    private Cat(User user, String name) {
        this.user = user;
        this.name = name;
    }

    public static Cat of(User user, String name) {
        return new Cat(user, name);
    }

    public OwnedItem purchaseItem(Item item) {
        Optional<OwnedItem> findOwnedItem = ownedItems.stream().filter(oi -> oi.getItem().equals(item)).findFirst();
        if (findOwnedItem.isPresent()) {
            throw new RuntimeException("이미 소유중인 아이템 입니다.");
        }

        Integer price = item.getPrice();

        if (price > user.getPoint()) {
            throw new RuntimeException("포인트가 부족합니다.");
        }

        user.spendPoints(price);

        OwnedItem newItem = OwnedItem.of(this, item);
        ownedItems.add(newItem);
        return newItem;
    }

    public void equip(OwnedItem ownedItem) {
        if (!ownedItems.contains(ownedItem)) {
            throw new RuntimeException("아이템을 소유하고 있지 않습니다.");
        }

        ItemCategoryEnum category = ownedItem.getItem().getCategory();
        if (equippedItem.containsKey(category)) {
            OwnedItem old = equippedItem.get(category);
            old.unUse();
        }

        ownedItem.use();
        equippedItem.put(category, ownedItem);
    }

    public void unEquip(OwnedItem ownedItem) {
        if (!ownedItems.contains(ownedItem)) {
            throw new RuntimeException("아이템을 소유하고 있지 않습니다.");
        }

        ItemCategoryEnum category = ownedItem.getItem().getCategory();
        if (!equippedItem.containsKey(category)) {
            return;
        }

        OwnedItem old = equippedItem.get(category);

        if (!old.equals(ownedItem)) {
            throw new RuntimeException(ownedItem + " 아이템을 착용하지 않았습니다.");
        }

        old.unUse();
        equippedItem.remove(category);
    }

    public List<OwnedItem> getEquippedItem() {
        return equippedItem.values().stream().toList();
    }
}
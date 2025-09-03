package com.example.demo.domain.cat;

import com.example.demo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "owned_item")
@Getter
public class OwnedItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private Cat cat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false)
    private Boolean isUsed;

    protected OwnedItem() {
    }

    private OwnedItem(Cat cat, Item item) {
        this.cat = cat;
        this.item = item;
    }

    public static OwnedItem of(Cat cat, Item item) {
        return new OwnedItem(cat, item);
    }

    public void use() {
        this.isUsed = true;
    }

    public void unUse() {
        this.isUsed = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OwnedItem ownedItem = (OwnedItem) o;

        return Objects.equals(id, ownedItem.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
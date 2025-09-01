package com.example.demo.domain.cat;

import com.example.demo.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "owned_item")
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

    public OwnedItem(Cat cat, Item item) {

    }
}
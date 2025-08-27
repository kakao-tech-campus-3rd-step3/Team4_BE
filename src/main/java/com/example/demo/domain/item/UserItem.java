package com.example.demo.domain.item;

import com.example.demo.domain.User;
import com.example.demo.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_item")
public class UserItem extends BaseEntity {

    @EmbeddedId
    private UserItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false)
    private Boolean isUsed = false;

    protected UserItem() {
    }

    private UserItem(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public void useItem() {
        this.isUsed = true;
    }

    public void cancelUse() {
        this.isUsed = false;
    }
}
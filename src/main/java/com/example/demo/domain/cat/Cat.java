package com.example.demo.domain.cat;

import com.example.demo.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
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
    private Map<ItemCategoryEnum, OwnedItem> equippedItem;

    protected Cat() {
    }

    //아이템 구매 메서드

    //아이템 착용 메서드

    //보유 아이템 목록 조회 메서드

    //착용한 아이템 목록 조회 메서드
}

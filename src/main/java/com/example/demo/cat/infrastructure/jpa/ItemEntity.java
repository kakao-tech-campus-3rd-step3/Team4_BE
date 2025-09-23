package com.example.demo.cat.infrastructure.jpa;

import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.cat.domain.Item;
import com.example.demo.common.infrastructure.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;

@Entity
@Table(name = "item")
@Getter

/**
 * 규칙: 동일한 아이템은 한 번만 구매할 수 있습니다.
 *
 * 사용자가 아이템을 구매한 이후에도, 해당 상품(ProductItem)의 옵션이 변경되더라도
 * 사용자가 구매했다는 사실은 여전히 유효해야 합니다.
 *
 * 문제점:
 * 만약 Item이 ProductItem의 일부 정보(name, category 등)만 보관한다면,
 * ProductItem이 업데이트될 경우 Item과 ProductItem을 비교할 때
 * 동일한 아이템인지 정확히 판단하기 어렵습니다.
 *
 * 설계 방향:
 * Item은 ProductItem을 직접 참조하거나 연동하여,
 * ProductItem이 변경되더라도 Item이 최신 상태를 반영하도록 해야 합니다.
 * 이렇게 하면 "동일한 아이템은 한 번만 구매 가능" 규칙을 항상 지킬 수 있습니다.
 */

public class ItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String name;

    //이건 뭐람
    @Column(nullable = false)
    private EquipSlot slot;

    @Column(nullable = false)
    private boolean equipped;

    protected ItemEntity() {
    }

    private ItemEntity(Long id, Long productId, String name, EquipSlot slot, boolean equipped) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.slot = slot;
        this.equipped = equipped;
    }

    public Item toModel() {
        return new Item(id, productId, name, slot, equipped);
    }

    public static ItemEntity fromModel(Item item) {
        return new ItemEntity(item.getId(), item.getProductId(), item.getName(), item.getSlot(),
            item.isEquipped());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemEntity itemEntity = (ItemEntity) o;

        return Objects.equals(id, itemEntity.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void equip(boolean equipped) {
        this.equipped = equipped;
    }
}
package com.example.demo.domain.cat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.cat.domain.EquippedItems;
import com.example.demo.cat.domain.Item;
import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.exception.errorcode.ItemErrorCode;
import com.example.demo.product.domain.DisplayImage;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CatTest {

    @Test
    void 고양이를_생성할_수_있다() {
        //given
        User user = new User(2L, "ktc@ktc.com", "테캠", 20000, "");
        Item item = new Item(1L, 45L, EquipSlot.HEAD, false);
        EquippedItems equippedItems = new EquippedItems();

        //when
        Cat cat = new Cat(
            user.getId(),
            "고앵이",
            List.of(item),
            equippedItems);

        //then
        assertThat(cat.getUserId()).isEqualTo(2L);
        assertThat(cat.getName()).isEqualTo("고앵이");
        List<Item> items = cat.getItems();
        assertThat(items.size()).isEqualTo(1);
        assertThat(items.contains(item)).isTrue();
        assertThat(cat.getEquippedItems().getEquippedItems().size()).isEqualTo(0);
    }

    @Test
    void 고양이는_아이템을_구매할_수_있다() {
        //given
        Cat cat = CatTestFixture.createCat();

        //when
        ProductItem product = CatTestFixture.createProductItem1();
        cat.purchaseItem(product);

        //then
        List<Item> items = cat.getItems();
        assertThat(items.size()).isEqualTo(1);
        Item item = items.get(0);
        assertThat(item.getProductId()).isEqualTo(product.getId());
        assertThat(item.getSlot()).isEqualTo(EquipSlot.HEAD);
        assertThat(item.isEquipped()).isFalse();
        assertThat(cat.getEquippedItems().getEquippedItems().size()).isEqualTo(0);
    }

    @Test
    void 고양이는_이미_보유하고_있는_상품은_재구매_할_수_없다() {
        //given
        Cat cat = CatTestFixture.createCat();

        //when
        ProductItem product = CatTestFixture.createProductItem1();
        cat.purchaseItem(product);

        assertThatThrownBy(
            () -> cat.purchaseItem(product)
        ).satisfies(
            ex -> {
                BusinessException e = (BusinessException) ex;
                assertThat(e.getErrorCode()).isEqualTo(ItemErrorCode.ITEM_ALREADY_EXIST);
            }
        ).isInstanceOf(BusinessException.class);
    }

    @Test
    void 고양이는_소유한_아이템을_착용할_수_있다() {
        //given
        Cat cat = CatTestFixture.createCat();
        ProductItem product = CatTestFixture.createProductItem1();
        Item item = new Item(1L, product.getId(), EquipSlot.HEAD, false);
        cat.getItems().add(item);

        //when
        cat.equip(item.getId());

        //then
        EquippedItems equippedItems = cat.getEquippedItems();
        assertThat(equippedItems.getEquippedItems().size()).isEqualTo(1);
        assertThat(item.isEquipped()).isTrue();
    }

    @Test
    void 고양이는_소유하지_않은_아이템을_착용할_경우_예외를_던진다() {
        //given
        Cat cat = CatTestFixture.createCat();
        ProductItem product = CatTestFixture.createProductItem1();
        Item item = new Item(1L, product.getId(), EquipSlot.HEAD, false);

        //when
        assertThatThrownBy(
            () -> cat.equip(item.getId())
        ).satisfies(
            ex -> {
                BusinessException e = (BusinessException) ex;
                assertThat(e.getErrorCode()).isEqualTo(ItemErrorCode.ITEM_NOT_EXIST);
            }
        ).isInstanceOf(BusinessException.class);
    }

    @Test
    void 고양이는_착용_아이템을_착용_해제할_수_있다() {
        //given
        Cat cat = CatTestFixture.createCat();
        ProductItem product = CatTestFixture.createProductItem1();
        Item item = new Item(1L, product.getId(), EquipSlot.HEAD, false);
        cat.getItems().add(item);
        cat.equip(item.getId());

        //when
        cat.unEquip(item.getId());

        //then
        EquippedItems equippedItems = cat.getEquippedItems();
        assertThat(equippedItems.getEquippedItems().size()).isEqualTo(0);
        assertThat(item.isEquipped()).isFalse();
    }

    @Test
    void 고양이의_이름을_변경할_수_있다() {
        //given
        Cat cat = CatTestFixture.createCat();

        //when
        cat.rename("사나운 고양이");

        //then
        assertThat(cat.getName()).isEqualTo("사나운 고양이");
    }


    public static class CatTestFixture {

        public static User createUser() {
            return new User(1L, "test@test.com", "카테캠", 10000, "");
        }

        public static Cat createCat() {
            User user = createUser();
            return new Cat(user.getId(), "고양yee", new ArrayList<>(), new EquippedItems());
        }

        public static ProductItem createProductItem1() {
            return new ProductItem(1L, "마법사 모자", 1000,
                EquipSlot.HEAD, new DisplayImage("testImg", 1.1f, 1.1f));
        }
    }

}

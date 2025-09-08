package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.domain.cat.Cat;
import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.domain.cat.OwnedItem;
import com.example.demo.domain.user.User;
import com.example.demo.dto.item.EquipItemRequest;
import com.example.demo.dto.item.OwnedItemResponse;
import com.example.demo.dto.item.ShopItemResponse;
import com.example.demo.repository.CatRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CatRepository catRepository;

    @Autowired
    EntityManager em;

    User user;
    Cat cat;

    Item item1;
    Item item2;
    Item item3;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.of("상욱", "tkddnr@test.com"));
        cat = catRepository.save(new Cat(user, "고양yee"));

        item1 = itemRepository.save(new Item("모자1", 1000, ItemCategoryEnum.HAT, "url1", 1.1f, 2.2f));
        item2 = itemRepository.save(new Item("모자2", 2000, ItemCategoryEnum.HAT, "url2", 3.3f, 4.4f));
        item3 = itemRepository.save(new Item("모자3", 3000, ItemCategoryEnum.HAT, "url3", 5.5f, 6.6f));
    }

    @Test
    void 사용자는_상점_아이템_목록을_불러올_수_있다() {
        //when
        Page<ShopItemResponse> shopItemResponses = itemService.listShopItems(1,
                ItemCategoryEnum.HAT, user);

        //then
        assertThat(shopItemResponses.hasContent()).isTrue();
        assertThat(shopItemResponses.getNumberOfElements()).isEqualTo(3);

        //정렬
        List<ShopItemResponse> list = shopItemResponses.stream().toList();
        ShopItemResponse shopItemResponse = list.get(0);
        assertThat(shopItemResponse.getId()).isEqualTo(item1.getId());

        //내용
        assertThat(shopItemResponse.getCategory()).isEqualTo(ItemCategoryEnum.HAT);
        assertThat(shopItemResponse.getName()).isEqualTo("모자1");
        assertThat(shopItemResponse.getPrice()).isEqualTo(1000);
        assertThat(shopItemResponse.getImageUrl()).isEqualTo("url1");
        assertThat(shopItemResponse.getIsOwned()).isFalse();
    }

    @Test
    void 사용자는_아이템을_구매할_수_있다() {
        //given
        user.earnPoints(3000);

        //when
        itemService.purchaseItem(item1.getId(), user);
        em.flush();
        OwnedItem ownedItem = cat.getOwnedItems().get(0);

        //then
        assertThat(ownedItem.getId()).isNotNull();
        assertThat(ownedItem.getItem()).isEqualTo(item1);
        assertThat(ownedItem.getCat()).isEqualTo(cat);
        assertThat(ownedItem.getIsUsed()).isFalse();
    }

    @Test
    void 이미_소유한_아이템을_구매하려고_할_경우_예외를_던진다() {
        //given
        user.earnPoints(3000);

        itemService.purchaseItem(item1.getId(), user);

        //when
        assertThatThrownBy(() -> itemService.purchaseItem(item1.getId(), user)).isInstanceOf(
                RuntimeException.class);
    }

    @Test
    void 아이템_구매_시_포인트가_부족한_경우_예외를_던진다() {
        //given
        user.earnPoints(500);

        //when
        assertThatThrownBy(() -> itemService.purchaseItem(item3.getId(), user)).isInstanceOf(
                RuntimeException.class);
    }

    @Test
    void 사용자는_소유한_아이템을_조회할_수_있다() {
        //given
        user.earnPoints(3000);
        itemService.purchaseItem(item1.getId(), user);
        itemService.purchaseItem(item2.getId(), user);

        em.flush();
        em.clear();

        //when
        List<OwnedItemResponse> responses = itemService.listOwnedItems(user);

        //then
        assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    void 사용자는_소유한_아이템을_착용할_수_있다() {
        //given
        user.earnPoints(3000);
        itemService.purchaseItem(item1.getId(), user);

        em.flush();
        em.clear();

        OwnedItem ownedItem = cat.getOwnedItems().get(0);

        //when
        EquipItemRequest equipItemRequest = new EquipItemRequest();
        equipItemRequest.setIsUsed(true);

        itemService.setItemEquipped(equipItemRequest, ownedItem.getId(), user);

        //then
        em.flush();
        em.clear();

        Cat getCat = catRepository.findByIdJoinFetchOwnedItems(cat.getId()).get();

        List<OwnedItem> equippedItem = getCat.getEquippedItem();
        assertThat(equippedItem.size()).isEqualTo(1);

        OwnedItem equipped = equippedItem.get(0);
        assertThat(equipped).isEqualTo(ownedItem);
    }

    @Test
    void 사용자는_같은_카테고리의_다른_아이템을_착용중일_때_새로운_아이템을_착용하면_기존_아이템이_자동으로_해제된다() {
        //given
        user.earnPoints(5000);
        itemService.purchaseItem(item1.getId(), user);
        itemService.purchaseItem(item3.getId(), user);

        em.flush();
        em.clear();

        OwnedItem ownedItem1 = cat.getOwnedItems().stream()
                .filter(i -> i.getItem().getId().equals(this.item1.getId())).findFirst().get();
        OwnedItem ownedItem3 = cat.getOwnedItems().stream()
                .filter(i -> i.getItem().getId().equals(this.item3.getId())).findFirst().get();

        EquipItemRequest equipItemRequest = new EquipItemRequest();
        equipItemRequest.setIsUsed(true);

        itemService.setItemEquipped(equipItemRequest, ownedItem1.getId(), user);

        //when
        itemService.setItemEquipped(equipItemRequest, ownedItem3.getId(), user);

        //then
        em.flush();
        em.clear();

        Cat getCat = catRepository.findByIdJoinFetchOwnedItems(cat.getId()).get();

        List<OwnedItem> equippedItem = getCat.getEquippedItem();
        assertThat(equippedItem.size()).isEqualTo(1);

        OwnedItem ownedItem = equippedItem.get(0);
        assertThat(ownedItem).isEqualTo(ownedItem3);
    }

    @Test
    void 소유하지_않은_아이템을_착용_시도하면_예외를_던진다() {
        //given
        OwnedItem ownedItem = OwnedItem.of(null, item1);

        //when
        assertThatThrownBy(() -> cat.equip(ownedItem)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void 사용자는_착용한_아이템을_착용_해제할_수_있다() {
        //given
        user.earnPoints(3000);
        itemService.purchaseItem(item1.getId(), user);

        em.flush();
        em.clear();

        OwnedItem ownedItem = cat.getOwnedItems().get(0);

        EquipItemRequest equipItemRequest = new EquipItemRequest();
        equipItemRequest.setIsUsed(true);

        itemService.setItemEquipped(equipItemRequest, ownedItem.getId(), user);

        em.flush();
        em.clear();

        //when
        equipItemRequest.setIsUsed(false);
        itemService.setItemEquipped(equipItemRequest, ownedItem.getId(), user);

        //then
        em.flush();
        em.clear();

        Cat getCat = catRepository.findByIdJoinFetchOwnedItems(cat.getId()).get();

        List<OwnedItem> equippedItem = getCat.getEquippedItem();
        assertThat(equippedItem).isEmpty();
    }

    @Test
    void 소유하지_않은_아이템을_착용_해제_시도하면_예외를_던진다() {
        //given
        OwnedItem ownedItem = OwnedItem.of(null, item1);

        //when
        assertThatThrownBy(() -> cat.unEquip(ownedItem)).isInstanceOf(RuntimeException.class);
    }

}

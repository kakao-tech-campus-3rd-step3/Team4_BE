package com.example.demo.service.item;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.cat.controller.dto.EquipItemRequest;
import com.example.demo.cat.controller.dto.ItemResponse;
import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.cat.domain.EquippedItems;
import com.example.demo.cat.domain.Item;
import com.example.demo.cat.service.CatRepository;
import com.example.demo.cat.service.CatService;
import com.example.demo.cat.service.ItemService;
import com.example.demo.product.domain.DisplayImage;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.product.service.ProductItemRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
public class ItemServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CatService catService;

    @Autowired
    CatRepository catRepository;

    @Autowired
    ProductItemRepository productItemRepository;

    @Autowired
    ItemService itemService;

    User user;
    ProductItem product;

    @BeforeEach
    void setUp() {
        User user = new User(null, "abc@test.com", "상욱", 10000, "");
        this.user = userRepository.save(user);

        catService.createCat(this.user, "고양이");

        this.product = productItemRepository.save(
            new ProductItem("마법사모자", 1000, EquipSlot.HEAD, new DisplayImage("url", 1.9f, 1.2f)));
    }

    @Test
    void 아이템을_구매할_수_있다() {
        //when
        itemService.purchaseItem(product.getId(), user);

        //then
        Optional<User> getUser = userRepository.findById(user.getId());
        assertThat(getUser).isPresent();
        assertThat(getUser.get().getPoint()).isEqualTo(9000);

        Optional<Cat> getCat = catRepository.findById(user.getId());
        assertThat(getCat).isPresent();
        Cat cat = getCat.get();
        assertThat(cat.getItems().size()).isEqualTo(1);
        Item item = cat.getItems().get(0);
        assertThat(item.getId()).isNotNull();
        assertThat(item.getProductId()).isEqualTo(product.getId());
        assertThat(item.getSlot()).isEqualTo(EquipSlot.HEAD);
        assertThat(item.isEquipped()).isFalse();
    }

    @Test
    void 구매한_아이템을_조회할_수_있다() {
        //given
        itemService.purchaseItem(product.getId(), user);

        //when
        List<ItemResponse> items = itemService.listItems(user);

        //then
        assertThat(items.size()).isEqualTo(1);
        ItemResponse itemResponse = items.get(0);
        assertThat(itemResponse.getId()).isNotNull();
        assertThat(itemResponse.getCategory()).isEqualTo(EquipSlot.HEAD);
        assertThat(itemResponse.getName()).isEqualTo(product.getName());

        DisplayImage displayImage = product.getImage();
        assertThat(itemResponse.getImageUrl()).isEqualTo(displayImage.getImageUrl());
        assertThat(itemResponse.getOffsetX()).isEqualTo(displayImage.getOffsetX());
        assertThat(itemResponse.getOffsetY()).isEqualTo(displayImage.getOffsetY());
        assertThat(itemResponse.getIsUsed()).isFalse();

    }

    @Test
    void 소유한_아이템을_장착할_수_있다() {
        // given
        itemService.purchaseItem(product.getId(), user);
        ItemResponse itemResponse = itemService.listItems(user).get(0);
        EquipItemRequest request = new EquipItemRequest();
        request.setIsUsed(true);

        // when
        itemService.setItemEquipped(request, itemResponse.getId(), user);

        // then
        Cat updatedCat = catRepository.findById(user.getId())
            .orElseThrow();
        EquippedItems equippedItems = updatedCat.getEquippedItems();
        assertThat(equippedItems).isNotNull();
        List<Item> items = equippedItems.getEquippedItems();
        assertThat(items.size()).isEqualTo(1);
        Item item = items.get(0);
        assertThat(item.getId()).isEqualTo(itemResponse.getId());

    }

    @Test
    void 장착_아이템을_해제할_수_있다() {
        // given
        itemService.purchaseItem(product.getId(), user);
        ItemResponse itemResponse = itemService.listItems(user).get(0);

        EquipItemRequest equipRequest = new EquipItemRequest();
        equipRequest.setIsUsed(true);
        itemService.setItemEquipped(equipRequest, itemResponse.getId(), user);

        EquipItemRequest unEquipRequest = new EquipItemRequest();
        unEquipRequest.setIsUsed(false);

        // when
        itemService.setItemEquipped(unEquipRequest, itemResponse.getId(), user);

        // then
        Cat updatedCat = catRepository.findById(user.getId())
            .orElseThrow();

        EquippedItems equippedItems = updatedCat.getEquippedItems();
        assertThat(equippedItems).isNotNull();
        assertThat(equippedItems.getEquippedItems()).isEmpty();
    }


}

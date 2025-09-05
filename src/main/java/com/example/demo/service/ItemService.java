package com.example.demo.service;

import com.example.demo.domain.cat.Cat;
import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.domain.cat.OwnedItem;
import com.example.demo.domain.user.User;
import com.example.demo.dto.item.CreateItemRequest;
import com.example.demo.dto.item.EquipItemRequest;
import com.example.demo.dto.item.OwnedItemResponse;
import com.example.demo.dto.item.ShopItemResponse;
import com.example.demo.repository.CatRepository;
import com.example.demo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private static final Integer SHOP_ITEM_PAGE_SIZE = 6;

    private final ItemRepository itemRepository;
    private final CatRepository catRepository;

    @Transactional(readOnly = true)
    public Page<ShopItemResponse> listShopItems(Integer page, ItemCategoryEnum category,
            User user) {
        Page<Item> items = itemRepository.findAllByCategoryOrderByIdAsc(
                PageRequest.of(page - 1, SHOP_ITEM_PAGE_SIZE), category);
        Cat cat = getCatById(user.getId());
        List<OwnedItem> ownedItems = cat.getOwnedItems();

        Set<Long> ownedItemIds = ownedItems.stream()
                .map(oi -> oi.getItem().getId())
                .collect(Collectors.toSet());

        return items.map(item -> new ShopItemResponse(item, ownedItemIds.contains(item.getId())));
    }

    public void purchaseItem(Long itemId, User user) {
        Cat cat = getCatById(user.getId());
        Item item = getItemById(itemId);
        cat.purchaseItem(item);
    }

    @Transactional(readOnly = true)
    public List<OwnedItemResponse> listOwnedItems(User user) {
        Cat cat = getCatById(user.getId());
        return cat.getOwnedItems().stream().map(OwnedItemResponse::new).toList();
    }

    public void setItemEquipped(EquipItemRequest request, Long id, User user) {
        Cat cat = getCatById(user.getId());

        List<OwnedItem> ownedItems = cat.getOwnedItems();
        OwnedItem ownedItem = ownedItems.stream()
                .filter(oi -> oi.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("아이템을 소유하고 있지 않습니다."));

        if (request.getIsUsed()) {
            cat.equip(ownedItem);
        } else {
            cat.unEquip(ownedItem);
        }
    }

    public void registerItem(CreateItemRequest request) {
        Item item = Item.of(
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.getImageUrl(),
                request.getOffsetX(),
                request.getOffsetY()
        );
        itemRepository.save(item);
    }

    private Cat getCatById(Long id) {
        return catRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("고양이를 찾는데 실패하였습니다."));
    }

    private Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이템을 찾는데 실패하였습니다."));
    }
}

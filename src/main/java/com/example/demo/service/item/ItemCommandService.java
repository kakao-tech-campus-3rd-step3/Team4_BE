package com.example.demo.service.item;

import com.example.demo.domain.cat.Cat;
import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.OwnedItem;
import com.example.demo.domain.user.User;
import com.example.demo.dto.item.CreateItemRequest;
import com.example.demo.dto.item.EquipItemRequest;
import com.example.demo.repository.CatRepository;
import com.example.demo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemCommandService {

    private final ItemRepository itemRepository;
    private final CatRepository catRepository;

    public void purchaseItem(Long itemId, User user) {
        Cat cat = getCatById(user.getId());
        Item item = getItemById(itemId);
        cat.purchaseItem(item);
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
        Item item = new Item(
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
        return catRepository.findByIdJoinFetchOwnedItems(id)
            .orElseThrow(() -> new RuntimeException("고양이를 찾는데 실패하였습니다."));
    }

    private Item getItemById(Long id) {
        return itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("아이템을 찾는데 실패하였습니다."));
    }
}

package com.example.demo.service.item;

import com.example.demo.domain.cat.Cat;
import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.domain.user.User;
import com.example.demo.dto.item.OwnedItemResponse;
import com.example.demo.dto.item.ShopItemResponse;
import com.example.demo.repository.CatRepository;
import com.example.demo.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemQueryService {

    private static final Integer SHOP_ITEM_PAGE_SIZE = 6;

    private final ItemRepository itemRepository;
    private final CatRepository catRepository;

    public Page<ShopItemResponse> listShopItems(Integer page, ItemCategoryEnum category,
        User user) {
        return itemRepository.findAllByCategoryOrderByIdAsc(
            PageRequest.of(page - 1, SHOP_ITEM_PAGE_SIZE), category, user.getId());
    }

    public List<OwnedItemResponse> listOwnedItems(User user) {
        Cat cat = getCatById(user.getId());
        return cat.getOwnedItems().stream().map(OwnedItemResponse::new).toList();
    }

    private Cat getCatById(Long id) {
        return catRepository.findByIdJoinFetchOwnedItems(id)
            .orElseThrow(() -> new RuntimeException("고양이를 찾는데 실패하였습니다."));
    }
}

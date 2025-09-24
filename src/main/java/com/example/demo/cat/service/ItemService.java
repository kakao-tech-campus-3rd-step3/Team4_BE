package com.example.demo.cat.service;

import com.example.demo.cat.controller.dto.EquipItemRequest;
import com.example.demo.cat.controller.dto.ItemResponse;
import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.product.controller.dto.ProductItemResponse;
import com.example.demo.product.domain.ProductItem;
import com.example.demo.product.service.ProductItemRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private static final Integer SHOP_ITEM_PAGE_SIZE = 6;

    private final ProductItemRepository productItemRepository;
    private final CatRepository catRepository;
    private final ItemQueryRepository itemQueryRepository;
    private final UserRepository userRepository;

    public void purchaseItem(Long productId, User user) {
        Cat cat = getCatById(user.getId());
        ProductItem product = getProductById(productId);
        cat.purchaseItem(product);
        user.spendPoints(product.getPrice());

        userRepository.save(user);
        catRepository.save(cat);
    }

    public void setItemEquipped(EquipItemRequest request, Long itemId, User user) {
        Cat cat = getCatById(user.getId());

        if (request.getIsUsed()) {
            cat.equip(itemId);
        } else {
            cat.unEquip(itemId);
        }

        catRepository.save(cat);
    }

    @Transactional(readOnly = true)
    public Page<ProductItemResponse> listProductItems(Integer page, EquipSlot category,
        User user) {
        return productItemRepository.findAllByCategoryOrderByIdAsc(
            PageRequest.of(page - 1, SHOP_ITEM_PAGE_SIZE), category, getCatById(user.getId()));
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> listItems(User user) {
        return itemQueryRepository.findAllByUserId(user.getId());
    }

    private Cat getCatById(Long id) {
        return catRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("고양이를 찾는데 실패하였습니다."));
    }

    private ProductItem getProductById(Long id) {
        return productItemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("아이템을 찾는데 실패하였습니다."));
    }
}

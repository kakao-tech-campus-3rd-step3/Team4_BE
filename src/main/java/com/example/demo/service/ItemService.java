package com.example.demo.service;

import com.example.demo.domain.cat.Item;
import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.repository.ItemRepository;
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

    private final ItemRepository itemRepository;

    public void getShopItems(Integer page, ItemCategoryEnum category) {

        Page<Item> items = itemRepository.findAllByCategory(PageRequest.of(page - 1, SHOP_ITEM_PAGE_SIZE), category);

    }
}

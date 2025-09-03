package com.example.demo.controller;

import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/")
    public void getShopItems(@RequestParam Integer page, @RequestParam ItemCategoryEnum category) {
        itemService.getShopItems(page, category);
    }
}

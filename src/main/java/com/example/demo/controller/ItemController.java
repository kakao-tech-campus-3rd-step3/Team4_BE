package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.dto.item.CreateItemRequest;
import com.example.demo.dto.item.EquipItemRequest;
import com.example.demo.dto.item.OwnedItemResponse;
import com.example.demo.dto.item.ShopItemResponse;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<Page<ShopItemResponse>> getShopItems(@RequestParam Integer page, @RequestParam ItemCategoryEnum category, User user) {
        return ResponseEntity.ok(itemService.listShopItems(page, category, user));
    }

    @PostMapping("/items/{id}")
    public ResponseEntity<Void> purchase(@PathVariable Long id, User user) {
        itemService.purchaseItem(id, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/items")
    public ResponseEntity<List<OwnedItemResponse>> getOwnedItems(User user) {
        return ResponseEntity.ok(itemService.listOwnedItems(user));
    }

    @PatchMapping("/me/items/{id}")
    public ResponseEntity<Void> setItemEquipped(@RequestBody EquipItemRequest request, @PathVariable Long id, User user) {
        itemService.setItemEquipped(request, id, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/items")
    public ResponseEntity<Void> registerItem(@RequestBody CreateItemRequest request, User user) {
        itemService.registerItem(request);
        return ResponseEntity.ok().build();
    }

}

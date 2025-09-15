package com.example.demo.controller;

import com.example.demo.config.auth.CurrentUser;
import com.example.demo.domain.cat.ItemCategoryEnum;
import com.example.demo.domain.user.User;
import com.example.demo.dto.item.CreateItemRequest;
import com.example.demo.dto.item.EquipItemRequest;
import com.example.demo.dto.item.OwnedItemResponse;
import com.example.demo.dto.item.ShopItemResponse;
import com.example.demo.service.item.ItemCommandService;
import com.example.demo.service.item.ItemQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemCommandService itemCommandService;
    private final ItemQueryService itemQueryService;

    @GetMapping("/items")
    public ResponseEntity<Page<ShopItemResponse>> getShopItems(@RequestParam Integer page,
        @RequestParam ItemCategoryEnum category, @CurrentUser User user) {
        return ResponseEntity.ok(itemQueryService.listShopItems(page, category, user));
    }

    @PostMapping("/items/{id}")
    public ResponseEntity<Void> purchase(@PathVariable Long id, @CurrentUser User user) {
        itemCommandService.purchaseItem(id, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/items")
    public ResponseEntity<List<OwnedItemResponse>> getOwnedItems(@CurrentUser User user) {
        return ResponseEntity.ok(itemQueryService.listOwnedItems(user));
    }

    @PatchMapping("/me/items/{id}")
    public ResponseEntity<Void> setItemEquipped(@RequestBody EquipItemRequest request,
        @PathVariable Long id, @CurrentUser User user) {
        itemCommandService.setItemEquipped(request, id, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/items")
    public ResponseEntity<Void> registerItem(@RequestBody CreateItemRequest request) {
        itemCommandService.registerItem(request);
        return ResponseEntity.ok().build();
    }

}

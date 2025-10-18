package com.example.demo.cat.controller;

import com.example.demo.cat.controller.dto.EquipItemRequest;
import com.example.demo.cat.controller.dto.ItemResponse;
import com.example.demo.cat.domain.EquipSlot;
import com.example.demo.cat.service.ItemService;
import com.example.demo.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.product.controller.dto.ProductItemResponse;
import com.example.demo.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<Page<ProductItemResponse>> getShopItems(@RequestParam Integer page,
        @RequestParam EquipSlot category, @CurrentUser User user) {
        return ResponseEntity.ok(itemService.listProductItems(page, category, user));
    }

    @PostMapping("/items/{id}")
    public ResponseEntity<Void> purchase(@PathVariable Long id, @CurrentUser User user) {
        itemService.purchaseItem(id, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/items")
    public ResponseEntity<List<ItemResponse>> getOwnedItems(@CurrentUser User user) {
        return ResponseEntity.ok(itemService.listItems(user));
    }

    @PatchMapping("/me/items/{id}")
    public ResponseEntity<Void> setItemEquipped(@RequestBody EquipItemRequest request,
        @PathVariable Long id, @CurrentUser User user) {
        itemService.setItemEquipped(request, id, user);
        return ResponseEntity.ok().build();
    }

}
package com.example.demo.common.admin.controller;

import com.example.demo.common.admin.controller.dto.CreateProductItemRequest;
import com.example.demo.common.admin.controller.dto.MissionStatsResponse;
import com.example.demo.common.admin.service.AdminService;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.product.domain.ProductItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/missions/stats/view")
    public String missionStatsPage(
        Model model,
        @RequestParam(required = false) MissionCategoryEnum category,
        @RequestParam(defaultValue = "exposureCount") String sortBy,
        @RequestParam(defaultValue = "DESC") String sortOrder) {
        List<MissionStatsResponse> stats = adminService.getMissionStats(category, sortBy,
            sortOrder);
        model.addAttribute("missionStats", stats);
        model.addAttribute("categories", MissionCategoryEnum.values());
        model.addAttribute("currentCategory", category);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        return "admin/mission-stats";
    }

    @GetMapping("/items")
    public String itemsPage(Model model) {
        List<ProductItem> items = adminService.listAllProducts();
        model.addAttribute("items", items);
        return "admin/items";
    }

    @GetMapping("/items/new")
    public String newItemPage(Model model) {
        model.addAttribute("itemRequest", new CreateProductItemRequest());
        return "admin/item-form";
    }

    @PostMapping("/items")
    public String registerItem(@ModelAttribute CreateProductItemRequest request) {
        adminService.registerItem(request);
        return "redirect:/admin/items";
    }

    @GetMapping("/items/{id}/edit")
    public String editItemPage(@PathVariable Long id, Model model) {
        ProductItem item = adminService.getProductById(id);

        CreateProductItemRequest itemRequest = new CreateProductItemRequest();
        itemRequest.setName(item.getName());
        itemRequest.setPrice(item.getPrice());
        itemRequest.setCategory(item.getSlot());
        itemRequest.setImageUrl(item.getImage().getImageUrl());
        itemRequest.setOffsetX(item.getImage().getOffsetX());
        itemRequest.setOffsetY(item.getImage().getOffsetY());

        model.addAttribute("itemId", id);
        model.addAttribute("itemRequest", itemRequest);
        return "admin/item-edit-form";
    }

    @PostMapping("/items/{id}/edit")
    public String updateItem(@PathVariable Long id,
        @ModelAttribute CreateProductItemRequest request) {
        adminService.updateProduct(id, request);
        return "redirect:/admin/items";
    }

    @PostMapping("/items/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return "redirect:/admin/items";
    }

    @GetMapping("/missions/stats")
    @ResponseBody
    public ResponseEntity<List<MissionStatsResponse>> getMissionStatsApi(
        @RequestParam(required = false) MissionCategoryEnum category,
        @RequestParam(defaultValue = "exposureCount") String sortBy,
        @RequestParam(defaultValue = "DESC") String sortOrder) {
        List<MissionStatsResponse> stats = adminService.getMissionStats(category, sortBy,
            sortOrder);
        return ResponseEntity.ok(stats);
    }

}

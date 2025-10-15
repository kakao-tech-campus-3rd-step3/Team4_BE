package com.example.demo.common.admin.controller;

import com.example.demo.common.admin.controller.dto.UpdateMissionPromotion;
import com.example.demo.common.admin.domain.MissionPromotion;
import com.example.demo.common.admin.service.AdminService;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

//    @PostMapping("/admin/items")
//    public ResponseEntity<Void> registerItem(@RequestBody CreateProductItemRequest request) {
//        adminService.registerItem(request);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/promotions")
    public String getPromotionsForm(
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "WAITING") CustomMissionStateEnum state) {
        Page<MissionPromotion> promotions = adminService.listPromotions(page, state);
        model.addAttribute("states", Arrays.asList(CustomMissionStateEnum.values()));
        model.addAttribute("currentState", state);
        model.addAttribute("promotions", promotions);
        return "admin/mission/promotion/promotions";
    }

    @PostMapping("/promotions/{id}/evaluate")
    public String evaluatePromotion(@PathVariable Long id) {
        adminService.evaluate(id);
        return "redirect:/admin/promotions?state=FILTERED";
    }

    @PostMapping("/promotions/{id}/promote")
    public String promoteMission(@PathVariable Long id) {
        adminService.promoteMission(id);
        return "redirect:/admin/promotions?state=PROMOTED";
    }

    @PostMapping("/promotions/{id}/reject")
    public String rejectMission(@PathVariable Long id) {
        adminService.rejectMission(id);
        return "redirect:/admin/promotions";
    }

    @GetMapping("/promotions/{id}/update")
    public String updateScoreForm(@PathVariable Long id, Model model) {
        MissionPromotion promotion = adminService.getPromotion(id);
        model.addAttribute("promotionId", promotion.getId());
        model.addAttribute("promotion", new UpdateMissionPromotion(
            promotion.getContent(),
            promotion.getCategory(),
            promotion.getScore().getSentimentScore(),
            promotion.getScore().getEnergyScore(),
            promotion.getScore().getCognitiveScore(),
            promotion.getScore().getRelationshipScore(),
            promotion.getScore().getStressScore(),
            promotion.getScore().getEmploymentScore(),
            promotion.getLevel()
        ));
        return "admin/mission/promotion/updateContents";
    }

    @PostMapping("/promotions/{id}/update")
    public String updateContents(
        @PathVariable Long id,
        @ModelAttribute UpdateMissionPromotion promotion
    ) {
        adminService.updatePromotion(id, promotion);
        return "redirect:/admin/promotions?state=FILTERED";
    }

}

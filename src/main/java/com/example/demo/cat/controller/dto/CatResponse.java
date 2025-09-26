package com.example.demo.cat.controller.dto;

import com.example.demo.product.domain.DisplayImage;
import java.util.List;
import lombok.Getter;

@Getter
public class CatResponse {

    private final String name;
    private final List<DisplayImage> equipped;

    public CatResponse(String name, List<DisplayImage> equipped) {
        this.name = name;
        this.equipped = equipped;
    }
}

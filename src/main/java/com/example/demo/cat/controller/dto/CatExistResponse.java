package com.example.demo.cat.controller.dto;

import lombok.Getter;

@Getter
public class CatExistResponse {

    private boolean isExist;

    public CatExistResponse(boolean isExist) {
        this.isExist = isExist;
    }
}

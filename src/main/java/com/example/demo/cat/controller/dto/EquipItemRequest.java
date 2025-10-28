package com.example.demo.cat.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipItemRequest {

    @NotNull(message = "{item.isUsed.NotNull}")
    private Boolean isUsed;
}

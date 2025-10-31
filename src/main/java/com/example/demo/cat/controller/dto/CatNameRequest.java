package com.example.demo.cat.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CatNameRequest {

    @NotBlank(message = "{cat.name.NotBlank}")
    @Size(max = 20, message = "{cat.name.Size}")
    private String name;

}

package com.example.demo.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Admin {

    private Long id;
    private String name;
    private String hashedPassword;
}

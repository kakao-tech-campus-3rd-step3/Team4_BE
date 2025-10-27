package com.example.demo.admin.infrastructure.jpa;

import com.example.demo.admin.domain.Admin;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "admin")
@Getter
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String hashedPassword;

    protected AdminEntity() {}

    public AdminEntity(Long id, String name, String hashedPassword) {
        this.id = id;
        this.name = name;
        this.hashedPassword = hashedPassword;
    }

    public AdminEntity fromModel(Admin model) {
        return new AdminEntity(model.getId(), model.getName(), model.getHashedPassword());
    }

    public Admin toModel() {
        return new Admin(id, name, hashedPassword);
    }
}

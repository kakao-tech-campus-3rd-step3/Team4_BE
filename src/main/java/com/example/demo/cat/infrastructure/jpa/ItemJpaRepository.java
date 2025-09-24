package com.example.demo.cat.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long> {

}

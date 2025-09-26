package com.example.demo.diary.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryJpaRepository extends JpaRepository<DiaryEntity, Long> {

}

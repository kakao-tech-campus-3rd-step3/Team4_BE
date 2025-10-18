package com.example.demo.chat.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LongTermMemoryJpaRepository extends JpaRepository<LongTermMemory, Long> {

}

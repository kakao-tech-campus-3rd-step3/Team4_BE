package com.example.demo.repository;

import com.example.demo.domain.cat.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CatRepository extends JpaRepository<Cat, Long> {

    //Cat 가져올 시 반드시 아래 메서드를 사용해주세요
    @Query("select distinct c from Cat c left join fetch c.ownedItems oi join fetch oi.item where c.id = :catId")
    Optional<Cat> findByIdJoinFetchOwnedItems(@Param("catId") Long id);
}

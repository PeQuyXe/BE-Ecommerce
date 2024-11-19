package com.example.demo.repository;

import com.example.demo.model.Rating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findAll();
    @Query("SELECT r FROM Rating r JOIN FETCH r.user u JOIN FETCH r.product p WHERE p.id = :prodId")
    List<Rating> findByProductIdWithUser(Integer prodId);

}

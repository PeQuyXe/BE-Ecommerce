package com.example.demo.repository;

import com.example.demo.model.ProductsRecent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRecentRepository extends JpaRepository<ProductsRecent, Integer> {
}

package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p ORDER BY p.sold DESC LIMIT 10")
    List<Product> findTopSoldProducts();

    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC LIMIT 8")
    List<Product> findNewProducts();

    @Query("SELECT p FROM Product p ORDER BY p.view DESC LIMIT 8")
    List<Product> findTopViewedProducts();

    @Query("SELECT p FROM Product p WHERE p.cateId = :categoryId ORDER BY p.sold DESC ")
    List<Product> findProductsByCategory(int categoryId);


}

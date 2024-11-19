package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p ORDER BY p.sold DESC LIMIT 10")
    List<Product> findTopSoldProducts();

    @Query("SELECT p FROM Product p ORDER BY p.createAt DESC LIMIT 8")
    List<Product> findNewProducts();

    @Query("SELECT p FROM Product p ORDER BY p.view DESC LIMIT 8")
    List<Product> findTopViewedProducts();

    @Query("SELECT p FROM Product p WHERE p.cateId = :categoryId ORDER BY p.sold DESC ")
    List<Product> findProductsByCategory(Integer categoryId);
    Optional<Product> findById(Integer id);


}

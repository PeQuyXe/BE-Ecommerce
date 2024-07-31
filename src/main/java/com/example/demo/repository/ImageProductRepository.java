package com.example.demo.repository;

import com.example.demo.model.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {
    List<ImageProduct> findByProdId(Long prodId);
}


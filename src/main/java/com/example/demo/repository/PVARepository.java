package com.example.demo.repository;

import com.example.demo.model.ProductVariantAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PVARepository extends JpaRepository<ProductVariantAttribute, Long> {
    List<ProductVariantAttribute> findByProductVariantId(Long productVariantId);
    List<ProductVariantAttribute> findByProdId(Long prodId);
}

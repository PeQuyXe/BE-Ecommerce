package com.example.demo.repository;
import com.example.demo.model.ProductVariant;
import com.example.demo.model.VariantsValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantsValueRepository extends JpaRepository<VariantsValue, Integer> {
    List<VariantsValue> findByProductVariant_Id(Integer productVariantId);
    List<VariantsValue> findByProductVariant(ProductVariant productVariant);
}

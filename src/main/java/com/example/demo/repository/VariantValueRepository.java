package com.example.demo.repository;

import com.example.demo.dto.AttributeValueDTO;
import com.example.demo.model.VariantValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantValueRepository extends JpaRepository<VariantValue, Long> {

    @Query("SELECT new com.example.demo.dto.AttributeValueDTO(a.displayName, av.valueName) " +
            "FROM VariantValue vv " +
            "JOIN vv.productVariant pv " +
            "JOIN vv.attributeValue av " +
            "JOIN av.attribute a " +
            "WHERE pv.prodId = :productId")
    List<AttributeValueDTO> findAttributeValuesByProductId(Long productId);
}

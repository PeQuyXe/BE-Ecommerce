package com.example.demo.repository;

import com.example.demo.model.Attribute;
import com.example.demo.model.AttributeValue;
import com.example.demo.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {
    List<AttributeValue> findByAttributeId(Integer attributeId);
    void deleteByAttributeId(Integer attributeId);
    Optional<AttributeValue> findByAttributeAndValueName(Attribute attribute, String valueName);
    List<AttributeValue> findByProductVariant(ProductVariant productVariant);

}
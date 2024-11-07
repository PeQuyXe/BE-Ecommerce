package com.example.demo.repository;

import com.example.demo.model.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {
    List<AttributeValue> findByAttributeId(Integer attributeId);
    void deleteByAttributeId(Integer attributeId);
}
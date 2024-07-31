package com.example.demo.service;

import com.example.demo.dto.AttributeValueDTO;
import com.example.demo.model.ProductVariant;
import com.example.demo.repository.ProductVariantRepository;
import com.example.demo.repository.VariantValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private VariantValueRepository variantValueRepository;

    public List<ProductVariant> getProductVariantsByProductId(Long productId) {
        return productVariantRepository.findByProdId(productId);
    }

    public List<AttributeValueDTO> getAttributeValuesByProductId(Long productId) {
        return variantValueRepository.findAttributeValuesByProductId(productId);
    }
}

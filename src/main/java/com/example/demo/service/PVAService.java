package com.example.demo.service;

import com.example.demo.model.ProductVariantAttribute;
import com.example.demo.repository.PVARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PVAService {

    @Autowired
    private PVARepository repository;

    public List<ProductVariantAttribute> getAllProductVariantAttributes() {
        return repository.findAll();
    }

    public List<ProductVariantAttribute> getProductVariantAttributesByProductVariantId(Long id) {
        return repository.findByProductVariantId(id);
    }

    public List<ProductVariantAttribute> getProductVariantAttributesByProdId(Long prodId) {
        return repository.findByProdId(prodId);
    }

    public ProductVariantAttribute saveProductVariantAttribute(ProductVariantAttribute productVariantAttribute) {
        return repository.save(productVariantAttribute);
    }

    public void deleteProductVariantAttribute(Long id) {
        repository.deleteById(id);
    }
}

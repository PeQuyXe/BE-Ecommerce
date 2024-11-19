package com.example.demo.service;

import com.example.demo.model.Attribute;
import com.example.demo.model.VariantsValue;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.VariantsValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantsValueService {
    private final VariantsValueRepository variantsValueRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    public VariantsValueService(VariantsValueRepository variantsValueRepository) {
        this.variantsValueRepository = variantsValueRepository;
    }
    public List<Attribute> getAllAttributesWithValues() {
        return attributeRepository.findAll();
    }
    public List<VariantsValue> getVariantDetailsByProdId(Integer prodId) {
        return variantsValueRepository.findByProductVariant_Id(prodId);
    }
}

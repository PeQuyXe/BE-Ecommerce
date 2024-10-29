package com.example.demo.service;

import com.example.demo.model.VariantsValue;
import com.example.demo.repository.VariantsValueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantsValueService {
    private final VariantsValueRepository variantsValueRepository;

    public VariantsValueService(VariantsValueRepository variantsValueRepository) {
        this.variantsValueRepository = variantsValueRepository;
    }

    public List<VariantsValue> getVariantDetailsByProdId(Integer prodId) {
        return variantsValueRepository.findByProductVariant_Id(prodId);
    }
}

package com.example.demo.service;

import com.example.demo.model.VariantValue;
import com.example.demo.repository.VariantValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantValueService {

    @Autowired
    private VariantValueRepository variantValueRepository;

    public List<VariantValue> getAllVariantValues() {
        return variantValueRepository.findAll();
    }

    public VariantValue getVariantValueById(Long id) {
        return variantValueRepository.findById(id).orElse(null);
    }
}

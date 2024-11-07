package com.example.demo.service;

import com.example.demo.model.Brand;
import com.example.demo.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllCategories() {
        return brandRepository.findAll();
    }
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Integer id) {
        return brandRepository.findById(id).orElse(null);
    }

    public void addBrand(Brand brand) {
        brandRepository.save(brand);
    }

    public void updateBrand(Integer id, Brand brand) {
        Brand existingBrand = brandRepository.findById(id).orElse(null);
        if (existingBrand != null) {
            existingBrand.setName(brand.getName());
            brandRepository.save(existingBrand);
        }
    }

    public void deleteBrand(Integer id) {
        brandRepository.deleteById(id);
    }

    public boolean checkBrandExists(String name) {
        return brandRepository.existsByName(name);
    }
}
package com.example.demo.service;

import com.example.demo.model.ProductsRecent;
import com.example.demo.repository.ProductsRecentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsRecentService {

    @Autowired
    private ProductsRecentRepository productsRecentRepository;

    public List<ProductsRecent> getAllProductsRecent() {
        return productsRecentRepository.findAll();
    }

    public ProductsRecent getProductRecentById(Integer id) {
        return productsRecentRepository.findById(id).orElse(null);
    }

    public ProductsRecent saveProductRecent(ProductsRecent product) {
        return productsRecentRepository.save(product);
    }

    public void deleteProductRecent(Integer id) {
        productsRecentRepository.deleteById(id);
    }
}

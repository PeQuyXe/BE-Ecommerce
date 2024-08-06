package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        productRepository.deleteById(id);
    }
    public List<Product> getTopSoldProducts() {

            return productRepository.findTopSoldProducts();

    }

    public List<Product> getNewProducts() {
        return productRepository.findNewProducts();
    }

    public List<Product> getTopViewedProducts() {
        return productRepository.findTopViewedProducts();
    }

    public List<Product> getProductsByCategory(int categoryId) {
        return productRepository.findProductsByCategory(categoryId);
    }
}

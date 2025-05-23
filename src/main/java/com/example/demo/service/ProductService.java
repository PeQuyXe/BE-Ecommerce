package com.example.demo.service;

import com.example.demo.model.ImageProduct;
import com.example.demo.model.Product;
import com.example.demo.repository.ImageProductRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageProductRepository imageProductRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProductsByIds(List<Integer> ids) {
        return productRepository.findAllById(ids);
    }

    public Product saveProduct(Product product) {
     
        return productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
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

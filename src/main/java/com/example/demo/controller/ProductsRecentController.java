package com.example.demo.controller;

import com.example.demo.model.ProductsRecent;
import com.example.demo.service.ProductsRecentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products-recent")
public class ProductsRecentController {

    @Autowired
    private ProductsRecentService productsRecentService;

    @GetMapping
    public List<ProductsRecent> getAllProductsRecent() {
        return productsRecentService.getAllProductsRecent();
    }

    @GetMapping("/{id}")
    public ProductsRecent getProductRecentById(@PathVariable Long id) {
        return productsRecentService.getProductRecentById(id);
    }

    @PostMapping
    public ProductsRecent createProductRecent(@RequestBody ProductsRecent product) {
        return productsRecentService.saveProductRecent(product);
    }

    @PutMapping("/{id}")
    public ProductsRecent updateProductRecent(@PathVariable Long id, @RequestBody ProductsRecent product) {
        product.setId(id);
        return productsRecentService.saveProductRecent(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductRecent(@PathVariable Long id) {
        productsRecentService.deleteProductRecent(id);
    }
}

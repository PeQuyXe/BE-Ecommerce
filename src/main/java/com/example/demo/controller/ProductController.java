package com.example.demo.controller;

import com.example.demo.service.ProductService;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (productService.getProductById(id) != null) {
            product.setId(id);
            return productService.saveProduct(product);
        } else {
            return null; // handle not found case
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    @GetMapping("product_sold")
    public ResponseEntity<List<Product>> getTopSoldProducts() {
        List<Product> products = productService.getTopSoldProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("new_product")
    public ResponseEntity<List<Product>> getNewProducts() {
        List<Product> products = productService.getNewProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("view_product")
    public ResponseEntity<List<Product>> getTopViewedProducts() {
        List<Product> products = productService.getTopViewedProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("relate_product")
    public ResponseEntity<List<Product>> getRelateProducts() {
        List<Product> products = productService.getRelateProducts();
        return ResponseEntity.ok(products);
    }
}

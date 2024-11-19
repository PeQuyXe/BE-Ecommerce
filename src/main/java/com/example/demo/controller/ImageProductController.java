package com.example.demo.controller;

import com.example.demo.model.ImageProduct;
import com.example.demo.service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/image-products")
public class ImageProductController {

    @Autowired
    private ImageProductService imageProductService;

    @GetMapping
    public List<ImageProduct> getAllImageProducts() {
        return imageProductService.getAllImageProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageProduct> getImageProductById(@PathVariable Integer id) {
        Optional<ImageProduct> imageProduct = imageProductService.getImageProductById(id);
        return imageProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ImageProduct>> getImageProductsByProductId(@PathVariable Integer productId) {
        List<ImageProduct> imageProducts = imageProductService.getImageProductsByProductId(productId);
        if (imageProducts.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(imageProducts);
        }
    }
    @PostMapping
    public ImageProduct createImageProduct(@RequestBody ImageProduct imageProduct) {
        return imageProductService.saveImageProduct(imageProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageProduct> updateImageProduct(@PathVariable Integer id, @RequestBody ImageProduct imageProductDetails) {
        Optional<ImageProduct> imageProduct = imageProductService.getImageProductById(id);
        if (imageProduct.isPresent()) {
            ImageProduct updatedImageProduct = imageProduct.get();
            updatedImageProduct.setImage(imageProductDetails.getImage());
            updatedImageProduct.setProdId(imageProductDetails.getProdId());
            imageProductService.saveImageProduct(updatedImageProduct);
            return ResponseEntity.ok(updatedImageProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImageProduct(@PathVariable Integer id) {
        if (imageProductService.getImageProductById(id).isPresent()) {
            imageProductService.deleteImageProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

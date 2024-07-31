package com.example.demo.controller;

import com.example.demo.dto.AttributeValueDTO;
import com.example.demo.model.ProductVariant;
import com.example.demo.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping("/variants/{productId}")
    public ResponseEntity<List<ProductVariant>> getProductVariantsByProductId(@PathVariable Long productId) {
        List<ProductVariant> productVariants = productVariantService.getProductVariantsByProductId(productId);
        return ResponseEntity.ok(productVariants);
    }

    @GetMapping("/attributes/{productId}")
    public ResponseEntity<List<AttributeValueDTO>> getAttributeValuesByProductId(@PathVariable Long productId) {
        List<AttributeValueDTO> attributeValues = productVariantService.getAttributeValuesByProductId(productId);
        return ResponseEntity.ok(attributeValues);
    }
}

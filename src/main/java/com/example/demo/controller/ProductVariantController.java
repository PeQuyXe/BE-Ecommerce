package com.example.demo.controller;

import com.example.demo.dto.VariantDetailDTO;
import com.example.demo.service.ProductVariantsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
public class ProductVariantController {

    private final ProductVariantsService productVariantsService;

    public ProductVariantController(ProductVariantsService productVariantsService) {
        this.productVariantsService = productVariantsService;
    }

    @GetMapping("/{prodId}")
    public ResponseEntity<List<VariantDetailDTO>> getVariantDetailsByProductId(@PathVariable Integer prodId) {
        List<VariantDetailDTO> variantDetails = productVariantsService.getVariantDetailsByProductId(prodId);
        return ResponseEntity.ok(variantDetails);
    }
}

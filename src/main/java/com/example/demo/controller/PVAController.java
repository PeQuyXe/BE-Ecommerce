package com.example.demo.controller;

import com.example.demo.model.ProductVariantAttribute;
import com.example.demo.service.PVAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variant-attributes")
public class PVAController {

    @Autowired
    private PVAService PVAService;

    @GetMapping
    public List<ProductVariantAttribute> getAllProductVariantAttributes() {
        return PVAService.getAllProductVariantAttributes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantAttribute> getProductVariantAttributeById(@PathVariable Long id) {
        return PVAService.getProductVariantAttributesByProductVariantId(id)
                .stream()
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public List<ProductVariantAttribute> getProductVariantAttributesByProdId(@RequestParam Long prodId) {
        return PVAService.getProductVariantAttributesByProdId(prodId);
    }

    @PostMapping
    public ProductVariantAttribute createProductVariantAttribute(@RequestBody ProductVariantAttribute productVariantAttribute) {
        return PVAService.saveProductVariantAttribute(productVariantAttribute);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductVariantAttribute> updateProductVariantAttribute(@PathVariable Long id, @RequestBody ProductVariantAttribute productVariantAttributeDetails) {
        return PVAService.getProductVariantAttributesByProductVariantId(id)
                .stream()
                .findFirst()
                .map(existingAttribute -> {
                    existingAttribute.setProductVariant(productVariantAttributeDetails.getProductVariant());
                    existingAttribute.setAttribute(productVariantAttributeDetails.getAttribute());
                    existingAttribute.setAttributeValue(productVariantAttributeDetails.getAttributeValue());
                    existingAttribute.setProdId(productVariantAttributeDetails.getProdId());
                    PVAService.saveProductVariantAttribute(existingAttribute);
                    return ResponseEntity.ok(existingAttribute);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductVariantAttribute(@PathVariable Long id) {
        PVAService.deleteProductVariantAttribute(id);
        return ResponseEntity.noContent().build();
    }
}

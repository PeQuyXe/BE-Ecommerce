package com.example.demo.controller;

import com.example.demo.model.Attribute;
import com.example.demo.model.VariantsValue;
import com.example.demo.service.VariantsValueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variants")
public class VariantValueController {
    private final VariantsValueService variantsValueService;

    public VariantValueController(VariantsValueService variantsValueService) {
        this.variantsValueService = variantsValueService;
    }

    @GetMapping("/{prodId}")
    public ResponseEntity<List<VariantsValue>> getVariantDetails(@PathVariable Integer prodId) {
        List<VariantsValue> variants = variantsValueService.getVariantDetailsByProdId(prodId);
        return ResponseEntity.ok(variants);
    }
    @GetMapping("/attributes")
    public ResponseEntity<List<Attribute>> getAllAttributesWithValues() {
        List<Attribute> attributes = variantsValueService.getAllAttributesWithValues();
        return ResponseEntity.ok(attributes);
    }
}

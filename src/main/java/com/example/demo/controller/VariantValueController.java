package com.example.demo.controller;


import com.example.demo.model.VariantValue;
import com.example.demo.service.VariantValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variant-values")
public class VariantValueController {

    @Autowired
    private VariantValueService variantValueService;

    @GetMapping
    public List<VariantValue> getAllVariantValues() {
        return variantValueService.getAllVariantValues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariantValue> getVariantValueById(@PathVariable Long id) {
        VariantValue variantValue = variantValueService.getVariantValueById(id);
        if (variantValue == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(variantValue);
    }
}

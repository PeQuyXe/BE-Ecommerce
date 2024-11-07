package com.example.demo.controller;

import com.example.demo.model.Brand;
import com.example.demo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;


    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Integer id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @PostMapping
    public ResponseEntity<String> addBrand(@RequestBody Brand brand) {
        brandService.addBrand(brand);
        return ResponseEntity.ok("Thương hiệu đã được thêm thành công.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBrand(@PathVariable Integer id, @RequestBody Brand brand) {
        brandService.updateBrand(id, brand);
        return ResponseEntity.ok("Thương hiệu đã được cập nhật thành công.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Integer id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Thương hiệu đã được xóa thành công.");
    }
}

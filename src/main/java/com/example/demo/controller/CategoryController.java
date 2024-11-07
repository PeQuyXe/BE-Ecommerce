package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> addCategory(
            @RequestParam String name,
            @RequestParam("image") MultipartFile imageFile) {
        String imageName = imageFile.getOriginalFilename(); // Lấy tên file ảnh
        categoryService.addCategory(name, imageName, imageFile);
        return ResponseEntity.ok("Thêm mới thành công.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        String imageName = imageFile != null ? imageFile.getOriginalFilename() : null;
        categoryService.updateCategory(id, name, imageName, imageFile);
        return ResponseEntity.ok("Cập nhật thành công.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Xoá thành công.");
    }
}

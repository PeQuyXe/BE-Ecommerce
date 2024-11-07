package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private final String uploadDir = "C:\\Users\\phamq\\ecommerce-app\\FinalProject_Ecommerces\\src\\assets\\category"; // Đường dẫn lưu ảnh

    public void addCategory(String name, String imageName, MultipartFile imageFile) {
        Category category = new Category();
        category.setName(name);
        category.setImage(imageName); // Lưu tên file vào database

        if (imageFile != null && !imageFile.isEmpty()) {
            saveFile(imageFile, imageName); // Lưu file vào thư mục
        }

        categoryRepository.save(category);
    }

    public void updateCategory(Integer id, String name, String imageName, MultipartFile imageFile) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        category.setName(name);
        if (imageName != null) {
            category.setImage(imageName); // Cập nhật tên file
            saveFile(imageFile, imageName); // Lưu file vào thư mục nếu có
        }

        categoryRepository.save(category);
    }

    private void saveFile(MultipartFile file, String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath); // Lưu file ảnh vào thư mục
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
    // Xóa ảnh cũ (nếu có)
    private void deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            Files.deleteIfExists(filePath); // Xóa file nếu nó tồn tại
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    // Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Lấy danh mục theo ID
    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    // Xóa danh mục
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}

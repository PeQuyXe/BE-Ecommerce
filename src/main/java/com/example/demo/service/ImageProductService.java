package com.example.demo.service;

import com.example.demo.model.ImageProduct;
import com.example.demo.repository.ImageProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageProductService {

    @Autowired
    private ImageProductRepository imageProductRepository;

    public List<ImageProduct> getAllImageProducts() {
        return imageProductRepository.findAll();
    }

    public Optional<ImageProduct> getImageProductById(Long id) {
        return imageProductRepository.findById(id);
    }

    public ImageProduct saveImageProduct(ImageProduct imageProduct) {
        return imageProductRepository.save(imageProduct);
    }

    public void deleteImageProduct(Long id) {
        imageProductRepository.deleteById(id);
    }
    public List<ImageProduct> getImageProductsByProductId(Long prodId) {
        return imageProductRepository.findByProdId(prodId);
    }
}

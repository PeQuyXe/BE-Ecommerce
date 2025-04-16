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

    // Lấy thông tin chi tiết tất cả các biến thể cho một sản phẩm dựa trên prodId
//    @GetMapping("/{prodId}")
//    public ResponseEntity<List<VariantDetailDTO>> getVariantDetailsByProductId(@PathVariable Integer prodId) {
//        List<VariantDetailDTO> variantDetails = productVariantsService.getVariantDetailsByProductId(prodId);
//        return ResponseEntity.ok(variantDetails);
//    }
    @GetMapping("/{prodId}")
    public ResponseEntity<List<VariantDetailDTO>> getVariantDetailsByProductId(@PathVariable Integer prodId) {
        List<VariantDetailDTO> variantDetails = productVariantsService.getVariantDetailsByProductId(prodId);

        if (variantDetails.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(variantDetails);
    }

    // Tạo mới một biến thể sản phẩm
    @PostMapping("/{prodId}")
    public ResponseEntity<Void> createProductVariant(@PathVariable Integer prodId,
                                                     @RequestBody VariantDetailDTO variantDetail) {
        try {
            productVariantsService.createProductVariant(prodId, variantDetail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Cập nhật thông tin của một biến thể sản phẩm
    @PutMapping("/update/{variantId}")
    public ResponseEntity<Void> updateProductVariant(@PathVariable Integer variantId,
                                                     @RequestBody VariantDetailDTO variantDetail) {
        try {
            productVariantsService.updateProductVariant(variantId, variantDetail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Xóa một biến thể sản phẩm dựa trên variantId
    @DeleteMapping("/delete/{variantId}")
    public ResponseEntity<Void> deleteProductVariant(@PathVariable Integer variantId) {
        try {
            productVariantsService.deleteProductVariant(variantId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.example.demo.dto;

import com.example.demo.model.ProductVariant;
import com.example.demo.model.VariantsValue;
import lombok.Data;

import java.util.List;

@Data
public class VariantDetailDTO {
    private ProductVariant productVariant;
    private List<VariantsValue> variantValues;
    private Integer id;
    private Integer price;      // Đảm bảo kiểu dữ liệu là Integer hoặc Double
    private Integer quantity;   // Đảm bảo kiểu dữ liệu là Integer
    private Integer salePrice;  // Đảm bảo kiểu dữ liệu là Integer hoặc Double
    private String color;       // Lưu màu sắc từ frontend
    private String ram;         // Lưu thông tin RAM
    private String rom;         // Lưu thông tin ROM
    private String pin;         // Lưu dung lượng pin

    public VariantDetailDTO(ProductVariant productVariant, List<VariantsValue> variantValues, Integer salePrice) {
        this.productVariant = productVariant;
        this.variantValues = variantValues;
        this.salePrice = salePrice;  // Gán giá sale vào DTO
    }

    // Thêm phương thức set giá trị các thuộc tính cho màu sắc, RAM, ROM, Pin
    public void setVariantValues(String color, String ram, String rom, String pin) {
        this.color = color;
        this.ram = ram;
        this.rom = rom;
        this.pin = pin;
    }
}
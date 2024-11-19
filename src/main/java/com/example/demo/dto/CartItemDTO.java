package com.example.demo.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Integer productVariantId;
    private Integer quantity;
    private Integer price; // Giá sản phẩm (dùng để tính tổng giá trong `CartDTO`)
}

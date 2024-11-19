package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Integer userId;
    private List<CartItemDTO> items; // Danh sách các mục trong giỏ hàng
}


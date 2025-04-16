package com.example.demo.dto;

import lombok.Data;

import java.util.Map;

@Data
public class OrderItemDTO {
    private Integer id;
    // private Integer productVariantId;
    private String productName;
    private Integer productId;
    private String productImage;
    private String productVariant;
    private Integer price;
    private Integer quantity;
    private Integer totalMoney;
    private Map<String, String> variantValues;

}

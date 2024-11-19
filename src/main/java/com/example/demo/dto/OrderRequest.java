package com.example.demo.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {
    private String orderCode;
    private LocalDateTime orderDate;
    private String address;
    private String fullname;
    private String phone;
    private String note;
    private Integer couponId;
    private Integer totalMoney;
    private Integer userId;
    private OrderStatusRequest orderStatus;
    private List<OrderItemRequest> orderItems;

    // Getters và setters...
    @Data
    public static class OrderStatusRequest {
        private Integer id;

        // Getters và setters...
    }
    @Data
    public static class OrderItemRequest {
        private Integer productId;
        private Integer quantity;
        private Integer price;
        private Integer totalMoney;

        // Getters và setters...
    }
}

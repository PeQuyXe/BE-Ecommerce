package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private String orderCode;
    private Integer userId;
    private String fullname;
    private String phone;
    private String address;
    private String note;
    private LocalDateTime orderDate;
    private Integer orderStatusId;
    private Integer totalMoney;
    private Integer couponId;
    private List<OrderItemDTO> orderItems;
    private OrderStatusDTO orderStatus;
}

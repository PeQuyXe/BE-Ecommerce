package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_code", nullable = false, length = 50)
    private String orderCode;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Integer userId;

    @Column(name = "fullname", nullable = false, length = 50)
    private String fullname;

    @Column(name = "phone", length = 12)
    private String phone;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "total_money", nullable = false)
    private int totalMoney;

    @Column(name = "coupon_id")
    private Integer couponId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Liên kết với tài khoản người dùng


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Quản lý quan hệ chính để tránh vòng lặp
    private List<OrderItem> orderItems;

    // Getters and Setters
}

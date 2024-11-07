package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String thumb;
    private String title;
    private Double value;
    @Column(name="min_amount")
    private Integer minAmount;
    private String expired;
    private Integer quantity;
    private String status;

    // Constructors, getters, and setters

}

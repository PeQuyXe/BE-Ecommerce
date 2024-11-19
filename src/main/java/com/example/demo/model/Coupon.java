package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
    private Date expired;
    private Integer quantity;
    private String status;

    // Constructors, getters, and setters

}

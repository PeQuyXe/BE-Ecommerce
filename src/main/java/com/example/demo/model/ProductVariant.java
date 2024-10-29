package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@Table(name = "product_variants")

public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "prod_id", nullable = false)
    private Integer prodId;

    private Integer price;
    private Integer quantity;
    private Double discount;

    // Getters and Setters
}

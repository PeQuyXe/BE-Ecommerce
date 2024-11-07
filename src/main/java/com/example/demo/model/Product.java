package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "product")

public class Product {
    @Id
    private Integer id;

    private String title;

    @Column(name = "brand_id")
    private Integer brandId;

    private int price;

    private int discount;

    @Column(name = "is_variant")
    private int isVariant;

    private int sold;

    private int quantity;

    private String thumb;

    @Column(name = "total_ratings")
    private float totalRating;

    @Column(name = "total_user_ratings")
    private Integer totalUserRatings;

    @Column(name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "cate_id")
    private Integer cateId;

    private String slug;

    private int status;

    private int view;

    @Column(name = "create_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "update_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String image;
    public Category() {}

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }
}

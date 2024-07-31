package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name="images_product")
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private Long prodId;

    // Constructors, getters, and setters
    public ImageProduct() {}

    public ImageProduct(Long id, String image, Long prodId) {
        this.id = id;
        this.image = image;
        this.prodId = prodId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }
// Getters and Setters
}

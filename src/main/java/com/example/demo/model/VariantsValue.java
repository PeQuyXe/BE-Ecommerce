package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "variants_value")
public class VariantsValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @ManyToOne
    @JoinColumn(name = "attribute_value_id", nullable = false)
    private AttributeValue attributeValue;

    // Getters and Setters
}

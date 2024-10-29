package com.example.demo.dto;

import com.example.demo.model.ProductVariant;
import com.example.demo.model.VariantsValue;

import java.util.List;

public class VariantDetailDTO {
    private ProductVariant productVariant;
    private List<VariantsValue> variantValues;

    public VariantDetailDTO(ProductVariant productVariant, List<VariantsValue> variantValues) {
        this.productVariant = productVariant;
        this.variantValues = variantValues;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public List<VariantsValue> getVariantValues() {
        return variantValues;
    }
}


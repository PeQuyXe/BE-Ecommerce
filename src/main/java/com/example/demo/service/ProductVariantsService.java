package com.example.demo.service;

import com.example.demo.dto.VariantDetailDTO;
import com.example.demo.model.ProductVariant;
import com.example.demo.model.VariantsValue;
import com.example.demo.repository.ProductVariantRepository;
import com.example.demo.repository.VariantsValueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductVariantsService {

    private final ProductVariantRepository productVariantRepository;
    private final VariantsValueRepository variantsValueRepository;

    public ProductVariantsService(
            ProductVariantRepository productVariantRepository,
            VariantsValueRepository variantsValueRepository) {
        this.productVariantRepository = productVariantRepository;
        this.variantsValueRepository = variantsValueRepository;
    }

    /**
     * Lấy danh sách tất cả các biến thể của sản phẩm dựa theo prodId.
     */
    public List<ProductVariant> getProductVariantsByProductId(Integer prodId) {
        return productVariantRepository.findByProdId(prodId);
    }

    /**
     * Lấy thông tin các giá trị biến thể (attribute và attribute value) của một biến thể sản phẩm cụ thể.
     */
    public List<VariantsValue> getVariantsValuesByProductVariantId(Integer productVariantId) {
        return variantsValueRepository.findByProductVariant_Id(productVariantId);
    }

    /**
     * Lấy chi tiết tất cả các biến thể và giá trị tương ứng cho một sản phẩm.
     */
    public List<VariantDetailDTO> getVariantDetailsByProductId(Integer prodId) {
        List<ProductVariant> productVariants = getProductVariantsByProductId(prodId);

        return productVariants.stream().map(variant -> {
            List<VariantsValue> variantValues = getVariantsValuesByProductVariantId(variant.getId());
            return new VariantDetailDTO(variant, variantValues);
        }).collect(Collectors.toList());
    }
}

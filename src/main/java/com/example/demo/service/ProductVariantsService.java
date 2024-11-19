package com.example.demo.service;

import com.example.demo.dto.VariantDetailDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductVariantsService {

    private final ProductVariantRepository productVariantRepository;
    private final VariantsValueRepository variantsValueRepository;
    private final ProductRepository productRepository;
    @Autowired
    private AttributeValueRepository attributeValueRepository;
    @Autowired
    private AttributeRepository attributeRepository;

    public ProductVariantsService(ProductVariantRepository productVariantRepository,
                                  VariantsValueRepository variantsValueRepository , ProductRepository productRepository) {
        this.productVariantRepository = productVariantRepository;
        this.variantsValueRepository = variantsValueRepository;
        this.productRepository = productRepository;


    }

    public Product getProductByProductVariant(ProductVariant productVariant) {
        // Sử dụng prodId để lấy sản phẩm từ ProductRepository
        return productRepository.findById(productVariant.getProdId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productVariant.getProdId()));
    }
    /**
     * Lấy danh sách tất cả các biến thể của sản phẩm dựa theo prodId.
     */
    public List<ProductVariant> getProductVariantsByProductId(Integer prodId) {
        return productVariantRepository.findByProdId(prodId);
    }

    /**
     * Tạo và lưu biến thể sản phẩm cùng các giá trị thuộc tính liên quan.
     */
    public void createProductVariant(Integer prodId, VariantDetailDTO variantDetail) {
        // 1. Tạo và lưu thông tin biến thể sản phẩm
        ProductVariant variant = new ProductVariant();
        variant.setProdId(prodId);
        variant.setPrice(variantDetail.getSalePrice());
        variant.setQuantity(variantDetail.getQuantity());

        productVariantRepository.save(variant); // Lưu ProductVariant vào DB

        // 2. Duyệt qua các thuộc tính và tạo VariantsValue
        List<VariantsValue> variantsValues = new ArrayList<>();
        Map<String, String> attributes = createAttributesMap(variantDetail);

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String attributeName = entry.getKey();
            String attributeValue = entry.getValue();

            if (attributeValue != null && !attributeValue.trim().isEmpty()) {
                Attribute attribute = getAttributeByName(attributeName);
                if (attribute != null) {
                    AttributeValue attributeValueEntity = getOrCreateAttributeValue(attribute, attributeValue);
                    VariantsValue variantsValue = new VariantsValue();
                    variantsValue.setProductVariant(variant);
                    variantsValue.setAttribute(attribute);
                    variantsValue.setAttributeValue(attributeValueEntity);
                    variantsValues.add(variantsValue);
                }
            }
        }

        // 3. Lưu tất cả VariantsValue vào DB
        variantsValueRepository.saveAll(variantsValues);
    }

    /**
     * Tạo map các thuộc tính từ DTO
     */
    private Map<String, String> createAttributesMap(VariantDetailDTO variantDetail) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("color", variantDetail.getColor());
        attributes.put("ram", variantDetail.getRam());
        attributes.put("rom", variantDetail.getRom());
        attributes.put("pin", variantDetail.getPin());
        return attributes;
    }

    /**
     * Lấy hoặc tạo mới Attribute theo tên
     */
    private Attribute getAttributeByName(String attributeName) {
        Optional<Attribute> attributeOptional = attributeRepository.findByName(attributeName);
        return attributeOptional.orElse(null);
    }

    /**
     * Lấy hoặc tạo mới AttributeValue theo Attribute và giá trị.
     */
    private AttributeValue getOrCreateAttributeValue(Attribute attribute, String valueName) {
        Optional<AttributeValue> attributeValueOptional = attributeValueRepository.findByAttributeAndValueName(attribute, valueName);
        return attributeValueOptional.orElseGet(() -> {
            AttributeValue newAttributeValue = new AttributeValue();
            newAttributeValue.setAttribute(attribute);
            newAttributeValue.setValueName(valueName);
            return attributeValueRepository.save(newAttributeValue);
        });
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
            return new VariantDetailDTO(variant, variantValues, variant.getPrice());  // Gửi thêm giá gốc trong DTO
        }).collect(Collectors.toList());
    }

    /**
     * Cập nhật thông tin của một biến thể sản phẩm.
     */
    public void updateProductVariant(Integer variantId, VariantDetailDTO variantDetail) {
        Optional<ProductVariant> existingVariant = productVariantRepository.findById(variantId);
        if (existingVariant.isPresent()) {
            ProductVariant variant = existingVariant.get();
            variant.setPrice(variantDetail.getSalePrice());
            variant.setQuantity(variantDetail.getQuantity());
            productVariantRepository.save(variant);
        } else {
            throw new RuntimeException("Product variant not found");
        }
    }

    /**
     * Xóa một biến thể sản phẩm dựa trên variantId.
     */
    public void deleteProductVariant(Integer variantId) {
        if (productVariantRepository.existsById(variantId)) {
            productVariantRepository.deleteById(variantId);
        } else {
            throw new RuntimeException("Không tìm thấy biến thể với ID: " + variantId);
        }
    }
}

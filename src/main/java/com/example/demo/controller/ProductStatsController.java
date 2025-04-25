package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.SearchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/stats")
public class ProductStatsController {

    @Autowired
    private SearchLogService searchLogService;

    @Autowired
    private ProductService productService;

    @GetMapping("/top-searched")
    public ResponseEntity<?> getTopSearchedProducts(@RequestParam(defaultValue = "8") int limit) {
        Set<ZSetOperations.TypedTuple<Object>> topResults = searchLogService.getTopSearchedProducts(limit);
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (ZSetOperations.TypedTuple<Object> tuple : topResults) {
            int prodId = Integer.parseInt(tuple.getValue().toString());
            double score = tuple.getScore();

            Product product = productService.getProductById(prodId);
            if (product != null) {
                Map<String, Object> productData = new HashMap<>();
                // Lấy tất cả các trường thông tin từ đối tượng product
                productData.put("prod_id", product.getId());
                productData.put("thumb", product.getThumb());
                productData.put("title", product.getTitle());
                productData.put("price", product.getPrice());
                productData.put("totalRating", product.getTotalRating());
                productData.put("discount", product.getDiscount());
                productData.put("search_count", (int) score); // Lấy số lần tìm kiếm (từ Redis hoặc logic khác)
                responseList.add(productData);
            }

        }

        return ResponseEntity.ok(responseList);
    }
}

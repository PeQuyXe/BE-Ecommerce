package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ImageProductService;
import com.example.demo.service.ProductService;
import com.example.demo.service.SearchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/image-search")
public class ImageSearchController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ImageProductService imageProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SearchLogService searchLogService;

    private final String AI_SERVER_URL = "http://localhost:5000/search";

    @PostMapping("/search")
    public ResponseEntity<?> searchImage(@RequestParam("image") MultipartFile file) {
        try {
//            System.out.println("Bắt đầu xử lý ảnh...");
            String imageHash = DigestUtils.md5DigestAsHex(file.getBytes());
//            System.out.println("Image Hash: " + imageHash);

            // Kiểm tra cache Redis
            String redisKey = "image:" + imageHash;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
                return ResponseEntity.ok(redisTemplate.opsForValue().get(redisKey));
            }

            // Gửi ảnh sang AI Server
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> aiResponse = restTemplate.postForEntity(AI_SERVER_URL, requestEntity, Map.class);

//            System.out.println("Kết quả AI Response: " + aiResponse.getStatusCode());

            if (aiResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AI server error");
            }

            // Xử lý kết quả
            List<Map<String, Object>> results = (List<Map<String, Object>>) aiResponse.getBody().get("results");
//            System.out.println("Kết quả AI: " + results);
            List<Map<String, Object>> enrichedResults = new ArrayList<>();

            for (Map<String, Object> res : results) {
                Integer prodId = (Integer) res.get("prod_id");
                Product product = productService.getProductById(prodId);
                if (product != null) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("prod_id", prodId);
                    data.put("title", product.getTitle());
                    data.put("discount", product.getDiscount());
                    data.put("totalRating", product.getTotalRating());
                    data.put("price", product.getPrice());
                    data.put("distance", res.get("distance"));
                    data.put("images", imageProductService.getImageProductsByProductId(prodId));
                    enrichedResults.add(data);
                }
            }

            redisTemplate.opsForValue().set(redisKey, enrichedResults, 30, TimeUnit.MINUTES);
            return ResponseEntity.ok(enrichedResults);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Search failed: " + e.getMessage());
        }
    }

}

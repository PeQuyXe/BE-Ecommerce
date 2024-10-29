package com.example.demo.controller;

import com.example.demo.model.Rating;
import com.example.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // GET: Lấy danh sách đánh giá cho sản phẩm
    @GetMapping("/{prodId}")
    public List<Rating> getRatingsByProductId(@PathVariable Long prodId) {
        return ratingService.getRatingsByProductId(prodId);
    }

    // Lấy đánh giá theo người dùng (kèm fullname)
//    @GetMapping("/user/{userId}")
//    public List<String> getRatingsByUserId(@PathVariable Long userId) {
//        return ratingService.getRatingsByUserId(userId).stream()
//                .map(rating -> String.format("User: %s - Rating: %d sao , Comment: %s",
//                        rating.getUser().getFullname(),
//                        rating.getStar(),
//                        rating.getComment()))
//                .collect(Collectors.toList());
//    }

    // POST: Thêm đánh giá mới
    @PostMapping
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
        Rating savedRating = ratingService.addRating(rating);
        return ResponseEntity.ok(savedRating);
    }

    // PUT: Cập nhật đánh giá
    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long id, @RequestBody Rating rating) {
        return ratingService.updateRating(id, rating)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Xóa đánh giá
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}

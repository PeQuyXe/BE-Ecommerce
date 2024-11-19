// RatingController.java
package com.example.demo.controller;

import com.example.demo.dto.RatingDTO;
import com.example.demo.model.Rating;
import com.example.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // Lấy tất cả các rating
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{prodId}")
    public List<Rating> getRatingsByProductId(@PathVariable Integer prodId) {
        return ratingService.getRatingsByProductId(prodId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable Integer id, @RequestBody Rating rating) {
        return ratingService.updateRating(id, rating)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Integer id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<?> addRating(@RequestBody RatingDTO ratingDTO) {
        try {
            Rating rating = ratingService.saveRating(ratingDTO);
            return new ResponseEntity<>(rating, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save rating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

// RatingService.java
package com.example.demo.service;

import com.example.demo.dto.RatingDTO;
import com.example.demo.model.Rating;
import com.example.demo.model.User;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Phương thức lấy tất cả các rating
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating saveRating(RatingDTO ratingDTO) {
        // Tìm đối tượng Product và User từ ID
        Optional<Product> product = productRepository.findById(ratingDTO.getProdId());
        Optional<User> user = userRepository.findById(ratingDTO.getUserId());

        if (product.isPresent() && user.isPresent()) {
            // Tạo đối tượng Rating
            Rating rating = new Rating();
            rating.setProduct(product.get()); // Thiết lập Product từ ID
            rating.setUser(user.get()); // Thiết lập User từ ID
            rating.setStar(ratingDTO.getStar());
            rating.setComment(ratingDTO.getComment());
            rating.setStatus(ratingDTO.getStatus());
            rating.setCreateAt(ratingDTO.getCreateAt());
            rating.setUpdateAt(ratingDTO.getUpdateAt());

            // Lưu vào cơ sở dữ liệu
            return ratingRepository.save(rating);
        } else {
            throw new RuntimeException("Product or User not found!");
        }
    }

    public List<Rating> getRatingsByProductId(Integer prodId) {
        return ratingRepository.findByProductIdWithUser(prodId);
    }

    public Optional<Rating> updateRating(Integer id, Rating ratingDetails) {
        return ratingRepository.findById(id)
                .map(rating -> {
                    rating.setStar(ratingDetails.getStar());
                    rating.setComment(ratingDetails.getComment());
                    rating.setStatus(ratingDetails.getStatus());
                    return ratingRepository.save(rating);
                });
    }

    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }
}

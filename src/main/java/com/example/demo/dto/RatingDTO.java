package com.example.demo.dto;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class RatingDTO {

    private Integer id;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Integer userId;
    private Integer prodId;
    private String title;
    private Integer star;
    private String comment;
    private Integer status;

    // Constructor
    public RatingDTO(Integer id, LocalDateTime createAt, LocalDateTime updateAt, String title, Integer star, String comment, Integer status, Integer userId, Integer prodId) {
        this.id = id;
        this.createAt = createAt;
        this.title = title;
        this.star = star;
        this.comment = comment;
        this.status = status;
        this.prodId = prodId;
        this.userId = userId;
        this.updateAt = updateAt;
    }
}

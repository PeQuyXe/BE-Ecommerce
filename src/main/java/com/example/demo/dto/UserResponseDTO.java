package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponseDTO {
    private String accessToken;
    private Integer id;
    private String fullname;
    private String email;
    private String avatar;
    private Integer roleId;
    private Date createAt;

    public UserResponseDTO(User user, String accessToken) {
        this.accessToken = accessToken;
        this.id = user.getId();
        this.fullname = user.getFullname();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.roleId = user.getRoleId();
        this.createAt = user.getCreateAt();
    }

    // Getters và Setters
}

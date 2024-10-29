package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private String email;
    private String password;
    private String avatar;
    private String address;
    private String phone;

    @Column(name = "is_block")
    private int isBlock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = true)
    private Role role;

    @Column(columnDefinition = "TEXT")
    private String accessToken;

    @Column(length = 500)
    private String refreshToken;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullname;
    private String email;
    private String password;
    private String avatar;
    private String address;
    private String phone;

    @Column(name = "isBlock")
    private Integer isBlock;

    @Column(name = "access_Token", columnDefinition = "TEXT")
    private String accessToken;

    @Column(name = "refresh_Token", length = 500)
    private String refreshToken;

    @Column(name = "create_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(name = "update_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role role;
    @Column(name = "role_id")
    private Integer roleId;


}

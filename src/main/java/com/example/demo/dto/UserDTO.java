package com.example.demo.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Data
public class UserDTO {

    private Integer id;
    private String fullname;
    private String email;
    private String password;
    private String phone;
    private String avatar;
    private String address;
    private Integer isBlock;
    private Integer roleId;
    private Date createAt;
    private Date updateAt;

    private String roleDescription;
    public UserDTO() {
    }
    public UserDTO(Integer id, String fullname, String email, String password, String avatar,
                   String address, String phone, Integer isBlock, String roleDescription,
                   Date createAt, Date updateAt, Integer RoleId) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.address = address;
        this.phone = phone;
        this.isBlock = isBlock;
        this.roleDescription = roleDescription;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.roleId = roleId;
    }
}

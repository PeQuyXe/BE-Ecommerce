package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userDTO) {
        User newUser = userService.createUser(userDTO);

        // Tạo accessToken và refreshToken cho người dùng mới
        String accessToken = userService.generateAccessToken(newUser.getEmail(), newUser.getRoleId(), newUser.getId());
        String refreshToken = userService.generateRefreshToken(newUser.getEmail(), newUser.getRoleId(), newUser.getId());

        // Lưu token vào người dùng
        newUser.setAccessToken(accessToken);
        newUser.setRefreshToken(refreshToken);

        // Cập nhật token vào cơ sở dữ liệu
        userService.updateUserTokens(newUser, accessToken, refreshToken);

        // Chuẩn bị response
        Map<String, Object> response = Map.of(
                "user", newUser,
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
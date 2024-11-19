package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Lấy tất cả người dùng
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Lấy người dùng theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Tạo người dùng mới
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userDTO) {
        // Tạo người dùng mới
        User newUser = userService.createUser(userDTO);

        // Tạo accessToken và refreshToken cho người dùng mới
        String accessToken = userService.generateAccessToken(newUser.getEmail(), newUser.getRoleId(), newUser.getId());
        String refreshToken = userService.generateRefreshToken(newUser.getEmail(), newUser.getRoleId(), newUser.getId());

        // Lưu token vào người dùng
        newUser.setAccessToken(accessToken);
        newUser.setRefreshToken(refreshToken);

        // Cập nhật token vào cơ sở dữ liệu
        userService.updateUserTokens(newUser, accessToken, refreshToken);

        // Chuẩn bị phản hồi
        Map<String, Object> response = Map.of(
                "user", newUser,
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Cập nhật thông tin người dùng
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        // Cập nhật thông tin người dùng
        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Xóa người dùng
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        // Xóa người dùng
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
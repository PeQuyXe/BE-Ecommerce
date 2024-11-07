package com.example.demo.controller;

import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.example.demo.service.FirebaseService;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String fullname = request.get("fullname");
        String email = request.get("email");
        String password = request.get("password");
        Date createAt = new Date();
        String avatar = "https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg";
        Integer isBlock = 0;

        String message = authService.registerUser(fullname, email, password, createAt, avatar, isBlock);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<String> accessTokenOpt = authService.authenticate(email, password);
        if (accessTokenOpt.isPresent()) {
            // Lấy accessToken từ Optional
            String accessToken = accessTokenOpt.get();

            // Tìm User để lấy thêm thông tin
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                // Tạo phản hồi chứa các thông tin cần thiết
                Map<String, Object> response = new HashMap<>();
                response.put("accessToken", accessToken);
                response.put("refreshToken", user.getRefreshToken());
                response.put("fullname", user.getFullname());
                response.put("email", user.getEmail());
                response.put("isBlock", user.getIsBlock());
                response.put("createAt", user.getCreateAt());
                response.put("avatar", user.getAvatar());
                response.put("role_id", user.getRoleId());

                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(401).body("Thông tin đăng nhập không chính xác");
    }

    @PostMapping(value = "/google", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateGoogleUser(@RequestBody Map<String, String> request) {
        String googleAccessToken = request.get("token");

        try {
            // Authenticate Google user and retrieve user information with JWT
            UserResponseDTO userResponse = firebaseService.authenticateGoogleUser(googleAccessToken);

            // Return the user response with user details and JWT
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            // Handle cases where token verification fails or other exceptions occur
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google token or error occurred.");
        }
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (authService.validateRefreshToken(refreshToken)) {
            String email = authService.getEmailFromToken(refreshToken);
            String newAccessToken = authService.generateAccessToken(email);

            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("accessToken", newAccessToken);
            }});
        } else {
            return ResponseEntity.status(401).body("Refresh Token không hợp lệ");
        }
    }
}

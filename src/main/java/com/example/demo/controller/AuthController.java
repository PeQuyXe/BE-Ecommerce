package com.example.demo.controller;

import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.service.AuthService;
import com.example.demo.service.FirebaseService;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // Đăng ký người dùng mới
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String fullname = request.get("fullname");
        String email = request.get("email");
        String password = request.get("password");
        Date createAt = new Date();
        String avatar = "https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg";
        Integer isBlock = 0;


        try {
            String message = authService.registerUser(fullname, email, password, createAt, avatar, isBlock);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đăng ký không thành công: " + e.getMessage());
        }
    }

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<String> accessTokenOpt = authService.authenticate(email, password);
        if (accessTokenOpt.isPresent()) {
            String accessToken = accessTokenOpt.get();

            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                Integer roleId = user.getRoleId();
                Integer userId = user.getId();

                // Tạo refreshToken
                String refreshToken = jwtTokenUtil.generateRefreshToken(email, roleId, userId);

                // Tạo phản hổi frontend chứa thông tin người dùng
                Map<String, Object> response = new HashMap<>();
                response.put("id", user.getId());
                response.put("password", user.getPassword());
                response.put("address", user.getAddress());
                response.put("phone", user.getPhone());
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);
                response.put("fullname", user.getFullname());
                response.put("email", user.getEmail());
                response.put("isBlock", user.getIsBlock());
                response.put("createAt", user.getCreateAt());
                response.put("updateAt", user.getUpdateAt());
                response.put("avatar", user.getAvatar());
                response.put("roleId", user.getRoleId());

                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(401).body("Thông tin đăng nhập không chính xác");
    }

    // Xác thực người dùng qua firebase
    @PostMapping(value = "/google", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateGoogleUser(@RequestBody Map<String, String> request) {
        String googleAccessToken = request.get("token");

        try {
            UserResponseDTO userResponse = firebaseService.authenticateGoogleUser(googleAccessToken);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Google: " + e.getMessage());
        }
    }

    // Làm mới accessToken và refreshToken
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (jwtTokenUtil.validateToken(refreshToken)) {
            String email = jwtTokenUtil.extractEmail(refreshToken);
            Integer roleId = jwtTokenUtil.extractRoleId(refreshToken);
            Integer userId = jwtTokenUtil.extractUserId(refreshToken);

            // Tạo mới accessToken
            String newAccessToken = jwtTokenUtil.generateAccessToken(email, roleId, userId);

            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("accessToken", newAccessToken);
            }});
        } else {
            return ResponseEntity.status(401).body("Refresh Token không hợp lệ");
        }
    }
}

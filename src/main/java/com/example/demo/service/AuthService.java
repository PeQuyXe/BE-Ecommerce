package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(String fullname, String email, String password, Date createAt , String avatar, Integer isBlock) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã tồn tại.");
        }
        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAvatar(avatar);
        user.setCreateAt(createAt);
        user.setIsBlock(isBlock);
        user.setRoleId(3);
        userRepository.save(user);
        return "Đăng ký thành công";
    }

    public Optional<String> authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            User user = userOpt.get();

            // Lấy thông tin từ user
            Integer roleId = user.getRoleId();
            Integer userId = user.getId();

            // Tạo accessToken và refreshToken với email, roleId và userId
            String accessToken = jwtTokenUtil.generateAccessToken(email, roleId, userId);
            String refreshToken = jwtTokenUtil.generateRefreshToken(email, roleId, userId);

            // Lưu accessToken và refreshToken vào User
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            // Trả về accessToken
            return Optional.of(accessToken);
        }
        return Optional.empty();
    }

    /**
     * Trích xuất roleId từ token trong request
     */
    public Integer extractRoleFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (token != null) {
            return jwtTokenUtil.extractRoleId(token);
        }
        return null;
    }

    /**
     * Trích xuất userId từ token trong request
     */
    public Integer extractUserIdFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (token != null) {
            return jwtTokenUtil.extractUserId(token);
        }
        return null;
    }

    /**
     * Lấy token từ header request
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    // Các phương thức khác như đăng ký, xác thực, tạo token, etc.
}

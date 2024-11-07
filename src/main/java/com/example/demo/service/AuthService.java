package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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

            // Tạo accessToken và refreshToken
            String accessToken = jwtTokenUtil.generateAccessToken(email);
            String refreshToken = jwtTokenUtil.generateRefreshToken(email);

            // Lưu accessToken và refreshToken vào User
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            // Trả về accessToken
            return Optional.of(accessToken);
        }
        return Optional.empty();
    }


//    public String generateRefreshToken(String email) {
//        return jwtTokenUtil.generateRefreshToken(email);
//    }

    public boolean validateRefreshToken(String refreshToken) {
        return jwtTokenUtil.validateToken(refreshToken);
    }

    public String getEmailFromToken(String token) {
        return jwtTokenUtil.extractEmail(token);
    }
    public String generateAccessToken(String email) {
        return jwtTokenUtil.generateAccessToken(email);
    }
}

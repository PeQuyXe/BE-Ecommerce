package com.example.demo.service;

import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class FirebaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Xác thực người dùng Google và tạo JWT cho phiên làm việc

    public UserResponseDTO authenticateGoogleUser(String googleAccessToken) throws FirebaseAuthException {
        // Xác thực Google token qua Firebase
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(googleAccessToken);
        String email = decodedToken.getEmail();
        String uid = decodedToken.getUid();
        String name = (String) decodedToken.getClaims().get("name");
        String picture = (String) decodedToken.getClaims().get("picture");

        // Kiểm tra xem người dùng đã tồn tại trong cơ sở dữ liệu chưa
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setAccessToken(googleAccessToken); // Cập nhật accessToken nếu người dùng đã tồn tại
        } else {
            // Nếu người dùng chưa tồn tại, tạo mới người dùng
            user = new User();
            user.setEmail(email);
            user.setFullname(name);
            user.setPassword(passwordEncoder.encode(uid)); // Mã hóa uid để sử dụng tạm thời làm mật khẩu
            user.setAvatar(picture);
            user.setRoleId(3); // Gán role mặc định (ví dụ: người dùng thông thường)
            user.setCreateAt(new Date());
            user.setIsBlock(0); // Mặc định không bị khóa
        }

        // Lưu hoặc cập nhật người dùng
        userRepository.save(user);

        // Tạo accessToken và refreshToken cho người dùng
        String accessToken = jwtTokenUtil.generateAccessToken(user.getEmail(), user.getRoleId(), user.getId()); // Sử dụng user.getId() nếu cần
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getEmail(), user.getRoleId(), user.getId());
        user.setRefreshToken(refreshToken);

        // Trả về UserResponseDTO với thông tin người dùng và các token
        return new UserResponseDTO(user, accessToken, refreshToken);
    }
}

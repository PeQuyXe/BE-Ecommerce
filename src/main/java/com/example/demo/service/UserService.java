package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Lấy tất cả người dùng
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAllUsersWithRoleDescription();
    }

    /**
     * Lấy người dùng theo ID
     */
    public UserDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Tạo người dùng mới
     */
    public User createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreateAt(new Date());
        user.setRoleId(userDTO.getRoleId());
        user.setId(userDTO.getId());
        return userRepository.save(user);
    }

    /**
     * Cập nhật thông tin người dùng
     */
    public User updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Cập nhật thông tin
        user.setFullname(userDTO.getFullname());
        user.setEmail(userDTO.getEmail());

        // Nếu password không rỗng, mã hóa password mới
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setAvatar(userDTO.getAvatar());
        user.setRoleId(userDTO.getRoleId());
        user.setIsBlock(userDTO.getIsBlock());
        user.setUpdateAt(new Date());

        return userRepository.save(user);
    }

    /**
     * Xóa người dùng
     */
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    /**
     * Chuyển đổi từ Entity sang DTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullname(user.getFullname());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setAvatar(user.getAvatar());
        dto.setRoleDescription(user.getRole() != null ? user.getRole().getDescription() : null);
        dto.setCreateAt(user.getCreateAt());
        dto.setUpdateAt(user.getUpdateAt());
        dto.setIsBlock(user.getIsBlock());
        return dto;
    }

    /**
     * Chuyển đổi từ DTO sang Entity
     */
    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setFullname(dto.getFullname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setAvatar(dto.getAvatar());
        user.setRoleId(dto.getRoleId());
        user.setIsBlock(dto.getIsBlock());
        return user;
    }

    /**
     * Tạo accessToken cho người dùng
     */
    public String generateAccessToken(String email, Integer roleId , Integer userId) {
        return jwtTokenUtil.generateAccessToken(email, roleId, userId);
    }

    /**
     * Tạo refreshToken cho người dùng
     */
    public String generateRefreshToken(String email, Integer roleId, Integer userId) {
        return jwtTokenUtil.generateRefreshToken(email, roleId, userId);
    }

    /**
     * Cập nhật accessToken và refreshToken của người dùng
     */
    public void updateUserTokens(User user, String accessToken, String refreshToken) {
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

}

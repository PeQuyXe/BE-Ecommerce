package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Cập nhật thông tin người dùng.
    public User updateUser(Integer id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFullname(userDetails.getFullname());
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(userDetails.getPassword());
                    user.setAvatar(userDetails.getAvatar());
                    user.setAddress(userDetails.getAddress());
                    user.setPhone(userDetails.getPhone());
                    user.setIsBlock(userDetails.getIsBlock());
                    user.setRole(userDetails.getRole());
                    user.setAccessToken(userDetails.getAccessToken());
                    user.setRefreshToken(userDetails.getRefreshToken());
                    user.setUpdateAt(userDetails.getUpdateAt());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Người dùng không tìm thấy !"));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}

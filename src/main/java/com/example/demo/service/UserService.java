package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
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
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

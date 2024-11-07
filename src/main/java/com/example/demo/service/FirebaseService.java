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

    /**
     * Authenticates a Google user based on the provided Google access token, and retrieves or creates
     * a user in the database, generating a JWT token for the session.
     *
     * @param googleAccessToken The Google ID token from the client.
     * @return UserResponseDTO containing user information and the JWT token.
     * @throws FirebaseAuthException if token verification fails.
     */
    public UserResponseDTO authenticateGoogleUser(String googleAccessToken) throws FirebaseAuthException {
        // Verify the Google access token with Firebase
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(googleAccessToken);
        String email = decodedToken.getEmail();
        String uid = decodedToken.getUid();
        String name = (String) decodedToken.getClaims().get("name");
        String picture = (String) decodedToken.getClaims().get("picture");

        // Check if the user already exists
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setAccessToken(googleAccessToken);
        } else {
            // Create a new user if not found
            user = new User();
            user.setEmail(email);
            user.setFullname(name);
            user.setPassword(passwordEncoder.encode(uid)); // Use UID as the password, securely hashed
            user.setAvatar(picture);
            user.setRoleId(3); // Assign a default role ID, adjust as needed
            user.setAccessToken(googleAccessToken);
            user.setCreateAt(new Date());
        }

        userRepository.save(user);

        // Generate a JWT token for the authenticated user
        String jwtToken = jwtTokenUtil.generateToken(user.getEmail(), 5 * 60 * 1000);

        return new UserResponseDTO(user, jwtToken);
    }
}

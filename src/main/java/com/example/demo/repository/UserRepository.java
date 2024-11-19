package com.example.demo.repository;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Rating;
import com.example.demo.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT new com.example.demo.dto.UserDTO(u.id, u.fullname, u.email, u.password, u.avatar, u.address, u.phone, u.isBlock, r.description, u.createAt, u.updateAt, u.roleId) " +
            "FROM User u JOIN Role r ON u.roleId = r.id")
    List<UserDTO> findAllUsersWithRoleDescription();

    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);
    boolean existsByEmail(String email);
    @Query("SELECT r FROM Rating r ORDER BY r.createAt DESC")
    List<Rating> findRecentRatings(Pageable pageable);

}

package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    // Getters and Setters
}

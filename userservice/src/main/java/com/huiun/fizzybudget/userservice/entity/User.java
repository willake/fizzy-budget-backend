package com.huiun.fizzybudget.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name="email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name="password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name="activated", nullable = false)
    private Boolean activated = false; // Use Boolean wrapper class for null safety

    @Column(name="provider", length = 50)
    private String provider;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}

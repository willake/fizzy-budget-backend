package com.huiun.fizzybudget.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRoleId.class) // Indicates that a composite key is used
public class UserRole implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}

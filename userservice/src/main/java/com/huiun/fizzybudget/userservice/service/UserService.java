package com.huiun.fizzybudget.userservice.service;

import com.huiun.fizzybudget.userservice.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void addRoleToUser(String username, String roleName);

    void removeRoleFromUser(String username, String roleName);

    User createUser(User user);
}

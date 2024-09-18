package com.huiun.fizzybudget.userservice.service;

import com.huiun.fizzybudget.userservice.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User createUser(User user);
}

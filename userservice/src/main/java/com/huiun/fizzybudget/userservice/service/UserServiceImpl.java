package com.huiun.fizzybudget.userservice.service;

import com.huiun.fizzybudget.userservice.entity.Role;
import com.huiun.fizzybudget.userservice.entity.User;
import com.huiun.fizzybudget.userservice.exception.RoleNotFoundException;
import com.huiun.fizzybudget.userservice.exception.UserAlreadyExistsException;
import com.huiun.fizzybudget.userservice.exception.UserNotFoundException;
import com.huiun.fizzybudget.userservice.repository.RoleRepository;
import com.huiun.fizzybudget.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<User> findUserByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public User createUser(User user) {

        // TODO: maybe do something to verify if the information is valid

        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username is already taken.");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email is already registered.");
        }

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.findByRoleId(roleId)
                .orElseThrow(RoleNotFoundException::new);

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.findByRoleId(roleId)
                .orElseThrow(RoleNotFoundException::new);

        user.getRoles().remove(role);
        userRepository.save(user);
    }
}

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

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

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

    @Override
    public void addRoleToUser(String username, String roleName) {
        Optional<User> user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(RoleNotFoundException::new);

        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.getRoles().add(role);
            userRepository.save(existingUser);
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        Optional<User> user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(RoleNotFoundException::new);

        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.getRoles().remove(role);
            userRepository.save(existingUser);
        }
        else {
            throw new UserNotFoundException();
        }
    }
}

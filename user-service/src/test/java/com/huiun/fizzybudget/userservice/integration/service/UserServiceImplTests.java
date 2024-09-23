package com.huiun.fizzybudget.userservice.integration.service;

import com.huiun.fizzybudget.sharedentities.Role;
import com.huiun.fizzybudget.sharedentities.User;
import com.huiun.fizzybudget.userservice.repository.RoleRepository;
import com.huiun.fizzybudget.userservice.repository.UserRepository;
import com.huiun.fizzybudget.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class UserServiceImplTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private User testUser;

    private Role userRole;
    private Role managerRole;

    @BeforeEach
    public void setUp() {
        userRole = new Role();
        userRole.setRoleName("ROLE_USER");
        userRole = roleRepository.save(userRole);

        managerRole = new Role();
        managerRole.setRoleName("ROLE_MANAGER");
        managerRole = roleRepository.save(managerRole);

        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPasswordHash(passwordEncoder.encode("testUser"));
        testUser.setEmail("testUser@gmail.com");
        testUser.setActivated(true);

        testUser.getRoles().add(userRole);

        userRepository.save(testUser);
    }

    @Test
    public void testCreateUserIntegration() {
        User user = new User();
        user.setUsername("newUser");
        user.setPasswordHash(passwordEncoder.encode("newUser"));
        user.setEmail("newUser@gmail.com");
        user.setActivated(true);
        userService.createUser(user);

        Optional<User> retrievedUser = userRepository.findByUsername("newUser");
        assertTrue(retrievedUser.isPresent());

        User savedUser = retrievedUser.get();
//        Should also create a role automatically
        assertEquals(1, savedUser.getRoles().size());
        assertEquals(userRole.getRoleName(), savedUser.getRoles().iterator().next().getRoleName());
    }

    @Test
    public void testAddRoleToUserIntegration() {
        userService.addRoleToUser(testUser.getUserId(), managerRole.getRoleId());

        Optional<User> retrievedUser = userRepository.findByUserId(testUser.getUserId());
        assertTrue(retrievedUser.isPresent());

        User savedUser = retrievedUser.get();
        assertEquals(2, savedUser.getRoles().size());
        assertTrue(savedUser.getRoles().stream()
                .anyMatch(role -> managerRole.getRoleName().equals(role.getRoleName())));
    }

    @Test
    public void testRemoveRoleFromUserIntegration() {
        userService.removeRoleFromUser(testUser.getUserId(), userRole.getRoleId());

        Optional<User> retrievedUser = userRepository.findByUserId(testUser.getUserId());
        assertTrue(retrievedUser.isPresent());

        User savedUser = retrievedUser.get();
        assertEquals(0, savedUser.getRoles().size());
    }
}

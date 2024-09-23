package com.huiun.fizzybudget.userservice.integration.repository;

import com.huiun.fizzybudget.sharedentities.Role;
import com.huiun.fizzybudget.sharedentities.User;
import com.huiun.fizzybudget.userservice.repository.RoleRepository;
import com.huiun.fizzybudget.userservice.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public void testSaveAndFindById() {
        User user = new User();
        user.setUsername("newUser");
        user.setPasswordHash(passwordEncoder.encode("newUser"));
        user.setEmail("newUser@gmail.com");
        user.setActivated(true);

        User createdUser = userRepository.save(user);
        assertNotNull(createdUser);
        assertEquals("newUser", createdUser.getUsername());

        Optional<User> retrievedUser = userRepository.findById(createdUser.getUserId());
        assertTrue(retrievedUser.isPresent());
        assertEquals("newUser", retrievedUser.get().getUsername());
    }

    @Test
    public void testUserRoleRelationship() {
        testUser.getRoles().add(managerRole);

        User savedUser = userRepository.save(testUser);

        assertEquals(2, savedUser.getRoles().size());
        assertTrue(savedUser.getRoles().stream()
                .anyMatch(role -> managerRole.getRoleName().equals(role.getRoleName())));
    }
}

package com.huiun.fizzybudget.userservice.integration.repository;

import com.huiun.fizzybudget.userservice.entity.Role;
import com.huiun.fizzybudget.userservice.entity.User;
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

    private User testuser;

    @BeforeEach
    public void setUp() {
        testuser = new User();
        testuser.setUsername("testuser");
        testuser.setPasswordHash(passwordEncoder.encode("test"));
        testuser.setEmail("test@test.com");
        testuser.setActivated(true);

        userRepository.save(testuser);
    }

    @Test
    public void testSaveAndFindById() {
        User user = new User();
        user.setUsername("myuser");
        user.setPasswordHash(passwordEncoder.encode("myuser"));
        user.setEmail("myuser@gmail.com");
        user.setActivated(true);

        User createdUser = userRepository.save(user);
        assertNotNull(createdUser);
        assertEquals("myuser", createdUser.getUsername());

        Optional<User> retrievedUser = userRepository.findById(createdUser.getUserId());
        assertTrue(retrievedUser.isPresent());
        assertEquals("myuser", retrievedUser.get().getUsername());
    }

    @Test
    public void testUserRoleRelationship() {
        Role role = new Role();
        role.setRoleName("ROLE_USER");

        Role userRole = roleRepository.save(role);
        testuser.getRoles().add(userRole);

        User savedUser = userRepository.save(testuser);

        assertEquals(1, savedUser.getRoles().size());
        assertEquals("ROLE_USER", savedUser.getRoles().iterator().next().getRoleName());
    }
}

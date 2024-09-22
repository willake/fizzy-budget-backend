package com.huiun.fizzybudget.userservice.integration.repository;

import com.huiun.fizzybudget.userservice.entity.User;
import com.huiun.fizzybudget.userservice.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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
    public void testCreateUser() {
        User user = new User();
        user.setUsername("myuser");
        user.setPasswordHash(passwordEncoder.encode("myuser"));
        user.setEmail("myuser@gmail.com");
        user.setActivated(true);

        User createdUser = userRepository.save(user);
        assertNotNull(createdUser);
        assertEquals("myuser", createdUser.getUsername());
    }
}

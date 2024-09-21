package com.huiun.fizzybudget.userservice.unit.service;

import com.huiun.fizzybudget.userservice.entity.Role;
import com.huiun.fizzybudget.userservice.entity.User;
import com.huiun.fizzybudget.userservice.repository.RoleRepository;
import com.huiun.fizzybudget.userservice.repository.UserRepository;
import com.huiun.fizzybudget.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser");
        user.setPasswordHash("hashedpassword");
        user.setActivated(true);
        user.setRoles(new HashSet<>());

        role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ROLE_USER");
    }

    @Test
    public void testFindUserByUserId() {
        when(userRepository.findByUserId(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserByUserId(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }
}

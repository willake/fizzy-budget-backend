package com.huiun.fizzybudget.userservice.unit.service;

import com.huiun.fizzybudget.common.entities.Role;
import com.huiun.fizzybudget.common.entities.User;
import com.huiun.fizzybudget.userservice.exception.RoleNotFoundException;
import com.huiun.fizzybudget.userservice.exception.UserNotFoundException;
import com.huiun.fizzybudget.common.repository.RoleRepository;
import com.huiun.fizzybudget.common.repository.UserRepository;
import com.huiun.fizzybudget.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private Role userRole;
    private Role managerRole;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // create default user role
        userRole = new Role();
        userRole.setRoleId(1L);
        userRole.setRoleName("ROLE_USER");

        // create a manager role
        managerRole = new Role();
        managerRole.setRoleId(1L);
        managerRole.setRoleName("ROLE_MANAGER");

        // create a test user with default role
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUsername("testUser");
        testUser.setEmail("testUser@gmail.com");
        testUser.setPasswordHash("testUser");
        testUser.setActivated(true);
        testUser.setRoles(new HashSet<>());
        testUser.getRoles().add(userRole);
    }

    @Test
    public void testFindUserByUserId_UserExists_ReturnUser() {
        when(userRepository.findByUserId(testUser.getUserId())).thenReturn(Optional.of(testUser));

        Optional<User> retrievedUser = userService.findUserByUserId(1L);

        assertTrue(retrievedUser.isPresent());
        assertEquals(1L, retrievedUser.get().getUserId());
    }

    @Test
    public void testFindUserByUserId_UserNotFound_ReturnEmptyOptional() {
        when(userRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

        Optional<User> retrievedUser = userService.findUserByUserId(2L);

        assertFalse(retrievedUser.isPresent());
    }

    @Test
    public void testAddRoleToUser_UserAndRoleExist_Success() {
        when(userRepository.findByUserId(testUser.getUserId())).thenReturn(Optional.of(testUser));
        when(roleRepository.findByRoleId(managerRole.getRoleId())).thenReturn(Optional.of(managerRole));

        userService.addRoleToUser(testUser.getUserId(), managerRole.getRoleId());

        Optional<User> retrievedUser = userRepository.findByUserId(testUser.getUserId());
        assertTrue(retrievedUser.isPresent());

        User savedUser = retrievedUser.get();
        assertEquals(2, savedUser.getRoles().size());
        assertTrue(savedUser.getRoles().stream()
                .anyMatch(role -> managerRole.getRoleName().equals(role.getRoleName())));
    }

    @Test
    public void testAddRoleToUser_UserNotFound_ThrowsUserNotFoundException() {
        when(userRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.addRoleToUser(999L, managerRole.getRoleId())
        );
    }

    @Test
    public void testAddRoleToUser_RoleNotFound_ThrowsRoleNotFoundException() {
        when(userRepository.findByUserId(testUser.getUserId())).thenReturn(Optional.of(testUser));

        RoleNotFoundException exception = assertThrows(
                RoleNotFoundException.class,
                () -> userService.addRoleToUser(1L, 999L)
        );
    }
}

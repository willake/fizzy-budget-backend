package com.huiun.fizzybudget.userservice.unit.controller;

import com.huiun.fizzybudget.userservice.controller.UserController;
import com.huiun.fizzybudget.userservice.entity.Role;
import com.huiun.fizzybudget.userservice.entity.User;
import com.huiun.fizzybudget.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private Role userRole;
    private Role managerRole;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
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
    public void testGetUserByUserId_UserExists_ReturnUser() throws Exception {
        when(userService.findUserByUserId(testUser.getUserId())).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // maybe also verify the content of the json
    }

    @Test
    public void testGetUserByUserId_UserNotFound_ReturnsNotFound() throws Exception {
        when(userService.findUserByUserId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/999"))
                .andExpect(status().isNotFound());
    }
}

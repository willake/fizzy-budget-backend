package com.huiun.fizzybudget.userservice.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

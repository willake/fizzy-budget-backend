package com.huiun.fizzybudget.userservice.payload;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}

package com.huiun.fizzybudget.userservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserRoleId implements Serializable {

    private Long user;
    private Long role;
}

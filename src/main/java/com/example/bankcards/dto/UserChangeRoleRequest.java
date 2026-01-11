package com.example.bankcards.dto;

import com.example.bankcards.entity.Role;

public class UserChangeRoleRequest {
    private Long id;
    private Role role;

    //getters
    public Long getId() { return id; }
    public Role getRole() { return role; }
}

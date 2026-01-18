package com.example.bankcards.dto;

import com.example.bankcards.entity.Role;

public class UserChangeRoleRequest {
    private Long id;
    private Role role;

    //getters
    public Long getId() { return id; }
    public Role getRole() { return role; }

    public void setId(Long id) {
        this.id = id;
    }
    public void setRole(Role role) {this.role = role; }
}

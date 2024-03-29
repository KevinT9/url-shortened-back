package com.dev.urlshortener.dto;


import com.dev.urlshortener.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private RoleEntity role;
}

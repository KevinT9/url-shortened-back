package com.dev.urlshortenerback.dto;


import com.dev.urlshortenerback.entity.RoleEntity;
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

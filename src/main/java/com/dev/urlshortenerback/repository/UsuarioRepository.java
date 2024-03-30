package com.dev.urlshortenerback.repository;

import com.dev.urlshortenerback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String username);
    UserEntity findByUsernameAndPassword(String username, String password);
}

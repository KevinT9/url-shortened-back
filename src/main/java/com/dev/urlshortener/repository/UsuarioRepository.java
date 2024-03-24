package com.dev.urlshortener.repository;

import com.dev.urlshortener.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}

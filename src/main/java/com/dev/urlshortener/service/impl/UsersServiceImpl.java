package com.dev.urlshortener.service.impl;

import com.dev.urlshortener.entity.UserEntity;
import com.dev.urlshortener.repository.UsuarioRepository;
import com.dev.urlshortener.service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity saveUser(UserEntity usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public UserEntity findUserByUsernameAndPassword(String username, String password) {
        password = passwordEncoder.encode(password);
        return usuarioRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public UserEntity getUserEntityAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getName() != null) {
            return usuarioRepository.findByUsername(authentication.getName());
        } else {
            return null;
        }
    }
}

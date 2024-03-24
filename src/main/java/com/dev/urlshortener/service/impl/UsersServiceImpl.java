package com.dev.urlshortener.service.impl;

import com.dev.urlshortener.entity.UserEntity;
import com.dev.urlshortener.repository.UsuarioRepository;
import com.dev.urlshortener.service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsuarioRepository usuarioRepository;

    public UsersServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserEntity saveUser(UserEntity usuario) {
        return usuarioRepository.save(usuario);
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

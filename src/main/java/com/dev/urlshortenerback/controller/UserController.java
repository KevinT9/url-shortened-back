package com.dev.urlshortenerback.controller;

import com.dev.urlshortenerback.dto.UserDTO;
import com.dev.urlshortenerback.entity.UserEntity;
import com.dev.urlshortenerback.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO user) {

        if (user != null && user.getUsername() != null && user.getPassword() != null) {
            UserEntity userEntity = usersService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
            if (userEntity != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    ResponseEntity<UserEntity> register(@RequestBody UserEntity userEntity) {
        userEntity.setCreated(new Date());

        if(usersService.findByUsername(userEntity.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userEntity = usersService.saveUser(userEntity);

        if (userEntity != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/modify")
    ResponseEntity<UserEntity> modify(@RequestBody UserEntity userEntity) {

        UserEntity userBuscado = usersService.findByUsername(userEntity.getUsername());

        if(userBuscado == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userEntity.setCreated(userBuscado.getCreated());
        userEntity = usersService.modifyUser(userEntity);

        if (userEntity != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userEntity);
        }

        return ResponseEntity.badRequest().build();
    }
}

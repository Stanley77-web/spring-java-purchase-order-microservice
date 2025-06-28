package com.dans.javaonboard.authentication.controllers;

import com.dans.javaonboard.authentication.dtos.LoginDto;
import com.dans.javaonboard.authentication.entities.UserEntity;
import com.dans.javaonboard.authentication.services.LoginService;
import com.dans.javaonboard.authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authentication/v1")
public class AuthenticationController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody UserEntity user) {
        return ResponseEntity.ok(loginService.login(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.createUser(user));
    }
}

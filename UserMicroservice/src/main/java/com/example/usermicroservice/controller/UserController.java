package com.example.usermicroservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermicroservice.exception.InvalidJwtException;
import com.example.usermicroservice.model.AuthDto;
import com.example.usermicroservice.model.JwtResponse;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserDto;
import com.example.usermicroservice.security.JwtProvider;
import com.example.usermicroservice.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService service;
    private final JwtProvider jwtProvider;

    @PostMapping("/registration")
    public User registerUser(@Valid @RequestBody UserDto user) {
        return service.registerUser(user);
    }

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody AuthDto user) {
        return service.login(user);
    }

    @GetMapping("/info")
    public User getUserInfo(HttpServletRequest request) {
        if (jwtProvider.validateToken(request.getHeader("Authorization"))) {
            return service.getUserInfo(request.getHeader("Authorization"));
        }
        throw new InvalidJwtException("Недействительный токен сесси!");
    }
}

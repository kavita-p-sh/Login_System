package com.kavita.loginsystem.controller;

import com.kavita.loginsystem.dto.LoginRequest;
import com.kavita.loginsystem.dto.RegisterRequest;
import com.kavita.loginsystem.service.UserService;
import com.kavita.loginsystem.service.impl.UserServiceImple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(
                userService.register(request.getEmail(), request.getPassword())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                userService.login(request.getEmail(), request.getPassword())
        );
    }
}
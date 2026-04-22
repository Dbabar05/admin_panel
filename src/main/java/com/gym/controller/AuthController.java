package com.gym.controller;

import com.gym.dto.AuthResponseDto;
import com.gym.dto.LoginDto;
import com.gym.dto.UserDto;
import com.gym.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto, @RequestParam String password) {
        return ResponseEntity.ok(authService.register(userDto, password));
    }
}

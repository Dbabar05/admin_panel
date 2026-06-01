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

    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponseDto> adminLogin(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.adminLogin(loginDto));
    }


    @PostMapping("/admin/register")
    public ResponseEntity<String> registerAdmin(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authService.registerAdmin(userDto));
    }

}

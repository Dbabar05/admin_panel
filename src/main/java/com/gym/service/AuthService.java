package com.gym.service;

import com.gym.dto.AuthResponseDto;
import com.gym.dto.LoginDto;
import com.gym.dto.UserDto;
import com.gym.entity.User;
import com.gym.repository.UserRepository;
import com.gym.security.JwtTokenProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final com.gym.repository.RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       com.gym.repository.RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleRepository = roleRepository;
    }

    public AuthResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);


        // 🔥 get email from authentication
        String email = authentication.getName();

        // 🔥 fetch your actual entity
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponseDto(token, user.getId());
    }

    public String register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(userDto, user, "role");
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        
        com.gym.entity.Role role = roleRepository.findByName(userDto.getRole().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userDto.getRole()));
        user.setRole(role);
        
        userRepository.save(user);

        return "User registered successfully";
    }
}

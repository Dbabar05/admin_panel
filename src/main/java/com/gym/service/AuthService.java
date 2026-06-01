package com.gym.service;

import com.gym.dto.AuthResponseDto;
import com.gym.dto.LoginDto;
import com.gym.dto.UserDto;
import com.gym.entity.AdminRole;
import com.gym.entity.AdminUser;
import com.gym.entity.GymRole;
import com.gym.repository.AdminRoleRepository;
import com.gym.repository.AdminUserRepository;
import com.gym.repository.GymRoleRepository;
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
    private final AdminUserRepository adminUserRepository;
    private final AdminRoleRepository adminRoleRepository;
    private final GymRoleRepository gymRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager,
                       AdminUserRepository adminUserRepository,
                       AdminRoleRepository adminRoleRepository,
                       GymRoleRepository gymRoleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.adminUserRepository = adminUserRepository;
        this.adminRoleRepository = adminRoleRepository;
        this.gymRoleRepository = gymRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResponseDto adminLogin(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        String token = jwtTokenProvider.generateToken(authentication);
        String userType = jwtTokenProvider.getUserType(token);

        if (!"ADMIN".equals(userType)) {
            throw new RuntimeException("Access denied: Not an Admin user");
        }

        AdminUser user = adminUserRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthResponseDto(token, user.getId());
    }


    public String registerAdmin(UserDto userDto) {
        if (adminUserRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Admin email already exists");
        }

        AdminUser user = new AdminUser();
        BeanUtils.copyProperties(userDto, user, "role");
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        
        AdminRole role = adminRoleRepository.findByName(userDto.getRole().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Admin Role not found: " + userDto.getRole()));
        user.setRole(role);
        
        adminUserRepository.save(user);
        return "Admin user registered successfully";
    }


}

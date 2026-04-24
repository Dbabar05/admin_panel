package com.gym.service;

import com.gym.communication.dto.OwnerCreationRequest;
import com.gym.communication.dto.OwnerResponse;
import com.gym.dto.UserDto;
import com.gym.entity.Role;
import com.gym.entity.User;
import com.gym.repository.RoleRepository;
import com.gym.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public OwnerResponse createOwner(OwnerCreationRequest request) {
        log.info("Creating owner for tenant: {}", request.getTenantId());

        Role ownerRole = roleRepository.findByName("OWNER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("OWNER");
                    return roleRepository.save(role);
                });

        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setRole(ownerRole);
        user.setTenantId(request.getTenantId());

        userRepository.save(user);
        
        log.info("Owner created successfully for email: {}", request.getEmail());

        OwnerResponse response = new OwnerResponse();
        response.setEmail(user.getEmail());
        response.setPassword(tempPassword);
        return response;
    }

    @Transactional
    public void createUser(UserDto userDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        boolean isCurrentUserOwner = auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_OWNER"));

        String requestedRoleName = userDto.getRole().toUpperCase();

        if (isCurrentUserOwner && (requestedRoleName.equals("OWNER") || requestedRoleName.equals("ADMIN"))) {
            log.warn("Access denied: OWNER attempted to create {}", requestedRoleName);
            throw new AccessDeniedException("Owner cannot create admin or owner accounts. Contact platform admin.");
        }

        Role role = roleRepository.findByName(requestedRoleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + requestedRoleName));

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);
        // TenantId would typically be derived from current user context, but let's assume it's set if available
        // user.setTenantId(...);

        userRepository.save(user);
    }
}

package com.gym.service;

import com.gym.entity.Permission;
import com.gym.entity.Role;
import com.gym.repository.PermissionRepository;
import com.gym.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public void updateRolePermissions(UUID roleId, List<String> permissionNames) {
        log.info("Updating permissions for role ID: {}", roleId);
        
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        Set<Permission> permissions = permissionNames.stream()
                .map(name -> permissionRepository.findByName(name)
                        .orElseGet(() -> {
                            Permission p = new Permission();
                            p.setName(name);
                            return permissionRepository.save(p);
                        }))
                .collect(Collectors.toSet());

        role.setPermissions(permissions);
        roleRepository.save(role);
        
        log.info("Successfully updated permissions for role ID: {}", roleId);
    }
}

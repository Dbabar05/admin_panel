package com.gym.service;

import com.gym.dto.ResponseDto;
import com.gym.dto.RoleDto;
import com.gym.dto.RoleResponseDto;
import com.gym.entity.AdminPermission;
import com.gym.entity.AdminRole;
import com.gym.repository.AdminPermissionRepository;
import com.gym.repository.AdminRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final AdminRoleRepository roleRepository;
    private final AdminPermissionRepository permissionRepository;

    public ResponseDto create(RoleDto dto) {

        Set<AdminPermission> permissions = permissionRepository.findAllById(dto.getPermissionIds())
                .stream().collect(Collectors.toSet());

        AdminRole role = AdminRole.builder()
                .name(dto.getName().toUpperCase())
                .permissions(permissions)
                .build();

        roleRepository.save(role);
        return ResponseDto.builder().code(201).message("Role Has been Created").build();
    }

    public ResponseDto update(UUID id, RoleDto dto) {

        AdminRole role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(dto.getName().toUpperCase());

        Set<AdminPermission> permissions = permissionRepository.findAllById(dto.getPermissionIds())
                .stream().collect(Collectors.toSet());

        role.setPermissions(permissions);

       roleRepository.save(role);
        return ResponseDto.builder().code(200).message("Role Has been Updated").build();
    }

    public RoleResponseDto getById(UUID id) {
        return map(roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found")));
    }

    public Set<RoleResponseDto> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toSet());
    }

    public ResponseDto delete(UUID id) {
        roleRepository.deleteById(id);
        return ResponseDto.builder().code(200).message("Role Has been Deleted").build();

    }

    public RoleResponseDto updatePermissions(UUID roleId, Set<UUID> permissionIds) {

        AdminRole role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Set<AdminPermission> permissions = permissionRepository.findAllById(permissionIds)
                .stream().collect(Collectors.toSet());

        role.setPermissions(permissions);

        return map(roleRepository.save(role));
    }

    private RoleResponseDto map(AdminRole role) {
        return RoleResponseDto.builder()
                .name(role.getName())
                .permissions(
                        role.getPermissions()
                                .stream()
                                .map(AdminPermission::getName)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}

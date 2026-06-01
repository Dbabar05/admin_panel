package com.gym.service;

import com.gym.dto.PermissionDto;
import com.gym.dto.ResponseDto;
import com.gym.entity.AdminPermission;
import com.gym.repository.AdminPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final AdminPermissionRepository permissionRepository;

    public PermissionDto create(PermissionDto dto) {
        AdminPermission permission = AdminPermission.builder()
                .name(dto.getName().toUpperCase())
                .build();

        return map(permissionRepository.save(permission));
    }

    public List<PermissionDto> getAll() {
        return permissionRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public PermissionDto update(UUID id, PermissionDto dto) {
        AdminPermission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        permission.setName(dto.getName().toUpperCase());

        return map(permissionRepository.save(permission));
    }

    public ResponseDto delete(UUID id) {
        permissionRepository.deleteById(id);
        return ResponseDto.builder().code(200).message("Permission Has been Deleted").build();
    }

    private PermissionDto map(AdminPermission p) {
        return PermissionDto.builder()
                .id(p.getId())
                .name(p.getName())
                .build();
    }
}
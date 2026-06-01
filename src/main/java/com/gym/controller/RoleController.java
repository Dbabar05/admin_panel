package com.gym.controller;

import com.gym.dto.ResponseDto;
import com.gym.dto.RoleDto;
import com.gym.dto.RoleResponseDto;
import com.gym.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody RoleDto dto) {
        ResponseDto responseDto =  roleService.create(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable UUID id, @RequestBody RoleDto dto) {
        ResponseDto responseDto =  roleService.update(id, dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getById(@PathVariable UUID id) {
        RoleResponseDto roleResponseDto =  roleService.getById(id);
        return new ResponseEntity<>(roleResponseDto, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Set<RoleResponseDto>> getAll() {
        Set<RoleResponseDto> roleResponseDtos =  roleService.getAll();
        return new ResponseEntity<>(roleResponseDtos, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable UUID id) {
        ResponseDto responseDto =  roleService.delete(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    // 🔥 Update ONLY permissions for a role
    @PutMapping("/{id}/permissions")
    public ResponseEntity<RoleResponseDto> updatePermissions(
            @PathVariable UUID id,
            @RequestBody Set<UUID> permissionIds) {

        RoleResponseDto roleResponseDto = roleService.updatePermissions(id, permissionIds);
        return new ResponseEntity<>(roleResponseDto, HttpStatus.OK);

    }
}
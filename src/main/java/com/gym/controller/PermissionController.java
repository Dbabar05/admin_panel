package com.gym.controller;


import com.gym.dto.PermissionDto;
import com.gym.dto.ResponseDto;
import com.gym.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PermissionDto> create(@RequestBody PermissionDto dto) {
        PermissionDto permissionDto =  permissionService.create(dto);

        return new ResponseEntity<>(permissionDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PermissionDto>> getAll() {

        List<PermissionDto> list =  permissionService.getAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionDto> update(@PathVariable UUID id, @RequestBody PermissionDto dto) {
        PermissionDto permissionDto =  permissionService.update(id, dto);
        return new ResponseEntity<>(permissionDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable UUID id) {
        ResponseDto responseDto =  permissionService.delete(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
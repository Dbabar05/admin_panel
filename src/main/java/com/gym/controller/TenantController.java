package com.gym.controller;

import com.gym.dto.ResponseDto;
import com.gym.dto.TenantDto;
import com.gym.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createTenant(@RequestBody TenantDto tenantDto) {
        ResponseDto responseDto = tenantService.createTenant(tenantDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TenantDto>> getAllTenants() {

        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    @GetMapping("/get-tenant/{id}")
    public ResponseEntity<TenantDto> getTenantById(@PathVariable UUID id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @PutMapping("/update-tenant/{id}")
    public ResponseEntity<ResponseDto> updateTenant(@PathVariable UUID id, @RequestBody TenantDto request) {

        return ResponseEntity.ok(tenantService.updateTenant(id, request));
    }

    @DeleteMapping("/delete-tenant/{id}")
    public ResponseEntity<ResponseDto> deleteTenant(@PathVariable UUID id) {
        ResponseDto responseDto=  tenantService.deleteTenant(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TenantDto> updateStatus(@PathVariable UUID id, @RequestParam String status) {
        return ResponseEntity.ok(tenantService.updateTenantStatus(id, status));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ResponseDto> activateTenant(@PathVariable UUID id) {
        ResponseDto responseDto = tenantService.activateTenant(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ResponseDto> deactivateTenant(@PathVariable UUID id) {
        ResponseDto responseDto = tenantService.deactivateTenant(id);
        return ResponseEntity.ok(responseDto);
    }
}

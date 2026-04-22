package com.gym.controller;

import com.gym.dto.TenantDto;
import com.gym.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<TenantDto> createTenant(@RequestBody TenantDto tenantDto) {
        return ResponseEntity.ok(tenantService.createTenant(tenantDto));
    }

    @GetMapping
    public ResponseEntity<List<TenantDto>> getAllTenants() {
        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDto> getTenantById(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TenantDto> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(tenantService.updateTenantStatus(id, status));
    }
}

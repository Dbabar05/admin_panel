package com.gym.controller;

import com.gym.dto.IntegrationDto;
import com.gym.dto.TenantIntegrationDto;
import com.gym.service.IntegrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/integrations")
public class IntegrationController {
    private final IntegrationService integrationService;

    public IntegrationController(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @GetMapping
    public ResponseEntity<List<IntegrationDto>> getAll() {
        return ResponseEntity.ok(integrationService.getAllIntegrations());
    }

    @PostMapping
    public ResponseEntity<IntegrationDto> create(@RequestBody IntegrationDto dto) {
        return ResponseEntity.ok(integrationService.createIntegration(dto));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<IntegrationDto> toggle(@PathVariable Long id) {
        return ResponseEntity.ok(integrationService.toggleIntegration(id));
    }

    @PostMapping("/tenant")
    public ResponseEntity<TenantIntegrationDto> enableForTenant(@RequestBody TenantIntegrationDto dto) {
        return ResponseEntity.ok(integrationService.enableForTenant(dto));
    }
}

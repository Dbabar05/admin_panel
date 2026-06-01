package com.gym.controller;

import com.gym.dto.integrationdto.CreateIntegrationRequest;
import com.gym.dto.integrationdto.IntegrationResponse;
import com.gym.enums.IntegrationType;
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
    public ResponseEntity<List<IntegrationResponse>> getAll() {
        return ResponseEntity.ok(integrationService.getAllIntegrations());
    }

    @GetMapping("/{service}")
    public ResponseEntity<IntegrationResponse> getByService(@PathVariable IntegrationType service) {
        return ResponseEntity.ok(integrationService.getIntegrationByService(service));
    }

    @PostMapping
    public ResponseEntity<IntegrationResponse> create(@RequestBody CreateIntegrationRequest dto) {
        return ResponseEntity.ok(integrationService.createIntegration(dto));
    }

    @PutMapping("/{service}")
    public ResponseEntity<IntegrationResponse> update(@PathVariable IntegrationType service, @RequestBody CreateIntegrationRequest dto) {
        return ResponseEntity.ok(integrationService.updateIntegration(service, dto));
    }

    @PatchMapping("/{service}/toggle")
    public ResponseEntity<IntegrationResponse> toggle(@PathVariable IntegrationType service) {
        return ResponseEntity.ok(integrationService.toggleIntegration(service));
    }

    @DeleteMapping("/{service}")
    public ResponseEntity<Void> delete(@PathVariable IntegrationType service) {
        integrationService.deleteIntegration(service);
        return ResponseEntity.noContent().build();
    }
}

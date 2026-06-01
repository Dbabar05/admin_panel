package com.gym.communication.client;

import com.gym.config.FeignClientConfig;
import com.gym.dto.integrationdto.CreateIntegrationRequest;
import com.gym.dto.integrationdto.IntegrationResponse;
import com.gym.dto.whatsapp.WhatsAppTemplateResponseDto;
import com.gym.enums.IntegrationType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "integration-service",
        url = "${integration.service.url}",
        configuration = FeignClientConfig.class
)
public interface IntegrationServiceClient {

    @GetMapping("/admin/integrations")
    List<IntegrationResponse> getAllIntegrations();

    @GetMapping("/admin/integrations/{service}")
    IntegrationResponse getIntegrationByService(
            @PathVariable("service") IntegrationType service
    );

    @PostMapping("/admin/integrations")
    IntegrationResponse createIntegration(
            @RequestBody CreateIntegrationRequest dto
    );

    @PutMapping("/admin/integrations/{service}")
    IntegrationResponse updateIntegration(
            @PathVariable("service") IntegrationType service,
            @RequestBody CreateIntegrationRequest dto
    );

    @PatchMapping("/admin/integrations/{service}/toggle")
    IntegrationResponse toggleIntegration(
            @PathVariable("service") IntegrationType service
    );

    @DeleteMapping("/admin/integrations/{service}")
    void deleteIntegration(
            @PathVariable("service") IntegrationType service
    );

    @PostMapping("/api/v1/whatsapp/templates/sync")
    void syncWhatsAppTemplates(@RequestHeader("X-Tenant-Id") UUID tenantId);

    @GetMapping("/api/v1/whatsapp/templates")
    List<WhatsAppTemplateResponseDto> getTemplates(@RequestHeader("X-Tenant-Id") UUID tenantId);
}

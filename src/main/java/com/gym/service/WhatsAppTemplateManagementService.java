package com.gym.service;


import com.gym.communication.client.IntegrationServiceClient;
import com.gym.util.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WhatsAppTemplateManagementService {

    private final IntegrationServiceClient integrationServiceClient;

    public void syncTemplates() {

        UUID tenantId = TenantContext.getCurrentTenant();

        integrationServiceClient.syncWhatsAppTemplates(tenantId);

        log.info("WhatsApp templates sync triggered for tenant {}", tenantId);
    }
}

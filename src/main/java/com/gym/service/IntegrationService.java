package com.gym.service;

import com.gym.communication.client.IntegrationServiceClient;
import com.gym.dto.integrationdto.CreateIntegrationRequest;
import com.gym.dto.integrationdto.IntegrationResponse;
import com.gym.entity.IntegrationRef;
import com.gym.enums.IntegrationType;
import com.gym.enums.SyncStatus;
import com.gym.repository.IntegrationRefRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class IntegrationService {

    private final IntegrationServiceClient integrationServiceClient;
    private final IntegrationRefRepository integrationRefRepository;

    public IntegrationService(IntegrationServiceClient integrationServiceClient,
                              IntegrationRefRepository integrationRefRepository) {
        this.integrationServiceClient = integrationServiceClient;
        this.integrationRefRepository = integrationRefRepository;
    }

    public List<IntegrationResponse> getAllIntegrations() {
        return integrationServiceClient.getAllIntegrations();
    }

    public IntegrationResponse getIntegrationByService(IntegrationType service) {
        return integrationServiceClient.getIntegrationByService(service);
    }

    public IntegrationResponse createIntegration(CreateIntegrationRequest dto) {
        IntegrationResponse created = integrationServiceClient.createIntegration(dto);
        syncSingleIntegration(created);
        return created;
    }

    public IntegrationResponse updateIntegration(IntegrationType service, CreateIntegrationRequest dto) {
        IntegrationResponse updated = integrationServiceClient.updateIntegration(service, dto);
        syncSingleIntegration(updated);
        return updated;
    }

    public IntegrationResponse toggleIntegration(IntegrationType service) {
        IntegrationResponse toggled = integrationServiceClient.toggleIntegration(service);
        syncSingleIntegration(toggled);
        return toggled;
    }

    public void deleteIntegration(IntegrationType service) {
        integrationServiceClient.deleteIntegration(service);
        integrationRefRepository.findByService(service).ifPresent(ref -> {
            ref.setActive(false);
            ref.setSyncedAt(LocalDateTime.now());
            integrationRefRepository.save(ref);
        });
    }

    public void syncIntegrations() {
        try {
            List<IntegrationResponse> remoteIntegrations = integrationServiceClient.getAllIntegrations();
            
            for (IntegrationResponse dto : remoteIntegrations) {
                syncSingleIntegration(dto);
            }
        } catch (Exception e) {
            log.error("Failed to sync integrations from Integration Service", e);
            // Optionally, mark all as FAILED if network is completely down
        }
    }

    private void syncSingleIntegration(IntegrationResponse dto) {
        IntegrationRef ref = integrationRefRepository.findByService(dto.getService())
                .orElse(new IntegrationRef());

        ref.setService(dto.getService());
        ref.setDisplayName(dto.getDisplayName());
        ref.setIntegrationAuthType(dto.getAuthType());
        ref.setActive(dto.getActive());
        ref.setSyncedAt(LocalDateTime.now());
        ref.setSyncStatus(SyncStatus.SYNCED);

        integrationRefRepository.save(ref);
    }
}

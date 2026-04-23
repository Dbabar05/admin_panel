package com.gym.communication.handler;

import com.gym.communication.client.CoreClient;
import com.gym.communication.dto.OwnerCreationRequest;
import com.gym.communication.dto.OwnerResponse;
import com.gym.communication.event.BaseEvent;
import com.gym.communication.event.TenantCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantCreatedCoreHandler implements EventHandler<TenantCreatedEvent> {

    private final CoreClient coreClient;

    @Override
    public boolean supports(BaseEvent event) {
        return event instanceof TenantCreatedEvent;
    }

    @Override
    public void handle(TenantCreatedEvent event) {
        log.info("Handling TenantCreatedEvent for tenant: {}", event.getTenantId());
        
        OwnerCreationRequest request = OwnerCreationRequest.builder()
                .tenantId(event.getTenantId())
                .email(event.getEmail())
                .name(event.getOwnerName())
                .build();

        try {
            OwnerResponse response = coreClient.createOwner(request);
            log.info("Successfully created owner in core for email: {}", response.getEmail());
        } catch (Exception e) {
            log.error("Failed to create owner in core for tenant: {}", event.getTenantId(), e);
            throw new RuntimeException("Core Application integration failed: " + e.getMessage(), e);
        }
    }
}

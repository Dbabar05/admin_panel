package com.gym.communication.handler;

import com.gym.communication.client.CoreClient;
import com.gym.communication.dto.OwnerCreationRequest;
import com.gym.communication.dto.OwnerResponse;
import com.gym.communication.dto.TenantRefRequest;
import com.gym.communication.event.BaseEvent;
import com.gym.communication.event.TenantCreatedEvent;
import com.gym.entity.Plan;
import com.gym.entity.Tenant;
import com.gym.repository.PlanRepository;
import com.gym.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantCreatedCoreHandler implements EventHandler<TenantCreatedEvent> {

    private final CoreClient coreClient;
    private final TenantRepository tenantRepository;
    private final PlanRepository planRepository;

    @Value("${internal.api.secret}")
    private String internalSecret;

    @Override
    public boolean supports(BaseEvent event) {
        return event instanceof TenantCreatedEvent;
    }

    @Override
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(TenantCreatedEvent event) {
        log.info("Core call started — creating owner for tenant: {}", event.getTenantId());

        Tenant tenant = tenantRepository.findById(event.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found: " + event.getTenantId()));

        try {

            log.info("Registering tenant in Core (TenantRef): {}", event.getTenantId());

            TenantRefRequest tenantRefRequest = TenantRefRequest.builder()
                    .tenantId(event.getTenantId())
                    .name(tenant.getGymName()) // 🔥 from DB
                    .status(tenant.getStatus()) // ensure it's string or enum name
                    .planCode(resolvePlanCode(tenant.getPlanId())) // 🔥 important
                    .build();

            coreClient.registerTenant(internalSecret, tenantRefRequest);

            log.info("Tenant registered successfully in Core: {}", event.getTenantId());

            OwnerCreationRequest request = OwnerCreationRequest.builder()
                    .tenantId(event.getTenantId())
                    .email(event.getEmail())
                    .name(event.getOwnerName())
                    .build();
            OwnerResponse response = coreClient.createOwner(internalSecret, request);

            log.info("Core response received — owner created with email: {}", response.getEmail());

            tenant.setOwnerStatus("SUCCESS");
            tenant.setOwnerEmail(response.getEmail());
            tenant.setOwnerTempPassword(response.getPassword());
            tenantRepository.save(tenant);

            log.info("Owner status updated to SUCCESS for tenant: {}", event.getTenantId());

        } catch (Exception e) {
            log.error("Core call failed for tenant: {} — reason: {}", event.getTenantId(), e.getMessage(), e);

            tenant.setOwnerStatus("FAILED");
            tenantRepository.save(tenant);

            log.warn("Owner status updated to FAILED for tenant: {}", event.getTenantId());
            // Do NOT rethrow — tenant creation must not be rolled back
        }
    }

    private String resolvePlanCode(UUID planId) {
        Plan plan = planRepository.findById(planId).orElseThrow();
        return plan.getName();
    }
}

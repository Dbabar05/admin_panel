package com.gym.security;

import com.gym.communication.client.CoreClient;
import com.gym.communication.dto.OwnerCreationRequest;
import com.gym.communication.dto.OwnerResponse;
import com.gym.dto.ResponseDto;
import com.gym.entity.Tenant;
import com.gym.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerAccountService {

    private final CoreClient coreClient;
    private final TenantRepository tenantRepository;

    @Value("${internal.api.secret}")
    private String internalSecret;


    public ResponseDto createOwner(OwnerCreationRequest request) {


        Tenant tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found: " + request.getTenantId()));

        try {
            OwnerResponse response = coreClient.createOwner(internalSecret, request);

            log.info("Core response received — owner created with email: {}", response.getEmail());


            tenant.setOwnerStatus("SUCCESS");
            tenant.setOwnerEmail(response.getEmail());
            tenant.setOwnerTempPassword(response.getPassword());
            tenantRepository.save(tenant);

            return ResponseDto.builder()
                    .code(200)
                    .message("Owner Account Created Successfully")
                    .build();
        } catch (Exception e) {
            log.error("Core call failed for tenant: {} — reason: {}", request.getTenantId(), e.getMessage(), e);

            tenant.setOwnerStatus("FAILED");
            tenantRepository.save(tenant);

            log.warn("Owner status updated to FAILED for tenant: {}", request.getTenantId());
            return ResponseDto.builder()
                    .code(400)
                    .message("Failed to Create the Owner")
                    .build();
        }
    }
}

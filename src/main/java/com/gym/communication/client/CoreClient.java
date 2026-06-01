package com.gym.communication.client;

import com.gym.communication.dto.OwnerCreationRequest;
import com.gym.communication.dto.OwnerResponse;
import com.gym.communication.dto.TenantRefRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "core-service", url = "${core.service.url}")
public interface CoreClient {

    @PostMapping("/internal/owners")
    OwnerResponse createOwner(
            @RequestHeader("X-Internal-Secret") String secret,
            @RequestBody OwnerCreationRequest request
    );

    @PostMapping("/internal/tenants")
    void registerTenant(
            @RequestHeader("X-Internal-Secret") String secret,
            @RequestBody TenantRefRequest request
    );
}

package com.gym.dto;

import lombok.Data;

@Data
public class TenantIntegrationDto {
    private Long id;
    private Long tenantId;
    private Long integrationId;
    private Boolean isEnabled;
}

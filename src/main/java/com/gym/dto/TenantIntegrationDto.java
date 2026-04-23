package com.gym.dto;

import lombok.Data;

@Data
public class TenantIntegrationDto {
    private java.util.UUID id;
    private java.util.UUID tenantId;
    private java.util.UUID integrationId;
    private Boolean isEnabled;
}

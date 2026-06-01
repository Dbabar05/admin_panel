package com.gym.dto.integrationdto;

import com.gym.enums.IntegrationAuthType;
import com.gym.enums.IntegrationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntegrationResponse {

    private UUID id;
    private IntegrationType service;
    private String displayName;
    private String description;
    private String icon;
    private String iconColor;
    private String iconBg;
    private IntegrationAuthType authType;
    private Boolean active;
    private String configSchema;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
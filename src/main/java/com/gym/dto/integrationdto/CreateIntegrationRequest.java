package com.gym.dto.integrationdto;

import com.gym.enums.IntegrationAuthType;
import com.gym.enums.IntegrationType;
import lombok.Data;

@Data
public class CreateIntegrationRequest {
    private IntegrationType service;
    private String displayName;
    private String description;
    private String icon;
    private String iconColor;
    private String iconBg;
    private IntegrationAuthType authType;
    private String configSchema;
    private Boolean active;
}

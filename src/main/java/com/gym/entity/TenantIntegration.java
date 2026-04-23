package com.gym.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tenant_integrations")
@Getter
@Setter
public class TenantIntegration extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(name = "integration_id", nullable = false)
    private java.util.UUID integrationId;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;
}

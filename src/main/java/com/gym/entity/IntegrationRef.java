package com.gym.entity;

import com.gym.enums.IntegrationAuthType;
import com.gym.enums.IntegrationType;
import com.gym.enums.SyncStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "admin_integration_refs")
@Getter
@Setter
public class IntegrationRef {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private IntegrationType service;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type")
    private IntegrationAuthType integrationAuthType;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    @Column(name = "synced_at")
    private LocalDateTime syncedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status")
    private SyncStatus syncStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

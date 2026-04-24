package com.gym.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tenants")
@Getter
@Setter
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "gym_name", nullable = false)
    private String gymName;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    private String phoneNumber;

    private String address;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String status; // active, suspended, onboarding

    @Column(name = "plan_id")
    private UUID planId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Owner provisioning tracking (Option A - stored on tenant)
    @Column(name = "owner_status")
    private String ownerStatus; // CREATING / SUCCESS / FAILED

    @Column(name = "owner_email")
    private String ownerEmail;

    @Column(name = "owner_temp_password")
    private String ownerTempPassword;
}

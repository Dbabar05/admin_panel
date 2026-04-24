package com.gym.entity;

import com.gym.util.TenantContext;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_users")
@EntityListeners(AuditingEntityListener.class) // 🔥 REQUIRED
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "tenant_id", nullable = true) // Allow null temporarily if there are existing users without tenantId, but ideally false. Wait, existing code didn't have tenantId on User, but the system relies on tenant-based users.
    private java.util.UUID tenantId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}

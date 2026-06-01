package com.gym.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "gym_authority",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "tenant_id", "roleCode","systemRole"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String roleCode;

    @Column(nullable = false)
    private String roleDescription;

    @Column(name = "tenant_id")
    private UUID tenantId;

    /**
     * true  -> Platform/System Role
     * false -> Tenant Custom Role
     */
    @Column(nullable = false)
    private Boolean systemRole = true;

//    @Column(name = "tenant_id", nullable = false)
//    protected UUID tenantId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "gym_role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<GymPermission> permissions = new HashSet<>();


}

package com.gym.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "admin_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "admin_role_permissions",
            joinColumns = @JoinColumn(name = "admin_role_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_permission_id")
    )
    private Set<AdminPermission> permissions;
}

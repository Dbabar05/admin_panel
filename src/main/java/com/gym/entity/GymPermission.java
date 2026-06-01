package com.gym.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "gym_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String permissionCode;

    @Column(nullable = false)
    private String permissionDescription;

    @Column(nullable = false)
    private String module;


}

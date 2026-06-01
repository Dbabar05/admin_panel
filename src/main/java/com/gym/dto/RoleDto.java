package com.gym.dto;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private UUID id;
    private String name;
    private Set<UUID> permissionIds;
}

package com.gym.controller;

import com.gym.communication.dto.OwnerCreationRequest;
import com.gym.communication.dto.OwnerResponse;
import com.gym.service.RoleService;
import com.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalController {

    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/owners")
    public ResponseEntity<OwnerResponse> createOwner(@RequestBody OwnerCreationRequest request) {
        log.info("Received internal request to create owner for tenant: {}", request.getTenantId());
        OwnerResponse response = userService.createOwner(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/roles/{roleId}/permissions")
    public ResponseEntity<String> updateRolePermissions(
            @PathVariable UUID roleId,
            @RequestBody List<String> permissions) {
        log.info("Received internal request to update permissions for role ID: {}", roleId);
        roleService.updateRolePermissions(roleId, permissions);
        return ResponseEntity.ok("Permissions updated successfully");
    }
}

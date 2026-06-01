package com.gym.config;

import com.gym.entity.GymPermission;
import com.gym.entity.GymRole;
import com.gym.repository.GymPermissionRepository;
import com.gym.repository.GymRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class GymDataInitializer implements CommandLineRunner {

    private final GymRoleRepository gymRoleRepository;
    private final GymPermissionRepository gymPermissionRepository;

    @Override
    public void run(String... args) {

        try {

            log.info("🚀 Initializing Gym Roles & Permissions...");

            initializePermissions();

            initializeRoles();

            initializeRolePermissionMappings();

            log.info("✅ Gym Roles & Permissions initialized successfully.");

        } catch (Exception e) {

            log.error("❌ Failed to initialize Gym RBAC", e);
        }
    }

    // =========================================================
    // PERMISSIONS
    // =========================================================

    @Transactional
    protected void initializePermissions() {

        createPermissionIfMissing(
                "CREATE_MEMBER",
                "Allows creating gym members",
                "MEMBERS"

        );

        createPermissionIfMissing(
                "UPDATE_MEMBER",
                "Allows updating gym members",
                "MEMBERS"
        );

        createPermissionIfMissing(
                "DELETE_MEMBER",
                "Allows deleting gym members",
                "MEMBERS"
        );

        createPermissionIfMissing(
                "VIEW_MEMBERS",
                "Allows viewing gym members",
                "MEMBERS"
        );

        createPermissionIfMissing(
                "VIEW_PAYMENTS",
                "Allows viewing payments",
                "MEMBERS"
        );

        createPermissionIfMissing(
                "MARK_ATTENDANCE",
                "Allows marking attendance",
                "MEMBERS"
        );

        createPermissionIfMissing(
                "UPDATE_WORKOUT",
                "Allows updating workout plans",
                "WORKOUT"
        );

        createPermissionIfMissing(
                "CHECK_WORKOUT",
                "Allows checking workout plans",
                "WORKOUT"
        );

        createPermissionIfMissing(
                "MANAGE_USERS",
                "Allows managing gym users",
                "USER"
        );

        createPermissionIfMissing(
                "MANAGE_PLANS",
                "Allows managing gym plans",
                "PLANS"
        );

        createPermissionIfMissing(
                "MANAGE_TRAINERS",
                "Allows managing trainers",
                "TRAINER"
        );

        createPermissionIfMissing(
                "VIEW_REPORTS",
                "Allows viewing reports",
                "REPORTS"

        );

        log.info("✅ Gym permissions initialized.");
    }

    private void createPermissionIfMissing(String permissionCode, String permissionDescription ,String module) {

        boolean exists = gymPermissionRepository.findByPermissionCode(permissionCode).isPresent();

        if (exists) {

            log.info("⏩ Permission already exists: {}", permissionCode);
            return;
        }

        GymPermission permission = GymPermission.builder()
                .permissionCode(permissionCode)
                .permissionDescription(permissionDescription)
                .module(module)
                .build();

        gymPermissionRepository.save(permission);

        log.info("➕ Created Gym Permission: {}", permissionCode);
    }

    // =========================================================
    // ROLES
    // =========================================================

    @Transactional
    protected void initializeRoles() {

        createRoleIfMissing(
                "OWNER",
                "Gym owner with full access"
        );

        createRoleIfMissing(
                "ADMIN",
                "Gym administrator"
        );

        createRoleIfMissing(
                "TRAINER",
                "Trainer with workout access"
        );

        createRoleIfMissing(
                "MEMBER",
                "Gym member role"
        );

        createRoleIfMissing(
                "RECEPTIONIST",
                "Reception/front desk role"
        );

        log.info("✅ Gym roles initialized.");
    }

    private void createRoleIfMissing(String roleCode, String roleDescription) {

        boolean exists = gymRoleRepository.findByRoleCode(roleCode).isPresent();

        if (exists) {

            log.info("⏩ Role already exists: {}", roleCode);

            return;
        }

        GymRole role = GymRole.builder()
                .roleCode(roleCode)
                .roleDescription(roleDescription)
                .permissions(new HashSet<>())
                .build();

        gymRoleRepository.save(role);

        log.info("➕ Created Gym Role: {}", roleCode);
    }

    @Transactional
    protected void initializeRolePermissionMappings() {

        assignPermissions(
                "OWNER",
                "CREATE_MEMBER",
                "UPDATE_MEMBER",
                "DELETE_MEMBER",
                "VIEW_MEMBERS",
                "VIEW_PAYMENTS",
                "MARK_ATTENDANCE",
                "UPDATE_WORKOUT",
                "CHECK_WORKOUT",
                "MANAGE_USERS",
                "MANAGE_PLANS",
                "MANAGE_TRAINERS",
                "VIEW_REPORTS"
        );

        assignPermissions(
                "ADMIN",
                "CREATE_MEMBER",
                "UPDATE_MEMBER",
                "VIEW_MEMBERS",
                "VIEW_PAYMENTS",
                "MARK_ATTENDANCE",
                "UPDATE_WORKOUT",
                "CHECK_WORKOUT",
                "MANAGE_PLANS",
                "VIEW_REPORTS"
        );

        assignPermissions(
                "TRAINER",
                "VIEW_MEMBERS",
                "MARK_ATTENDANCE",
                "UPDATE_WORKOUT",
                "CHECK_WORKOUT"
        );

        assignPermissions(
                "MEMBER",
                "VIEW_PAYMENTS",
                "CHECK_WORKOUT"
        );

        assignPermissions(
                "RECEPTIONIST",
                "CREATE_MEMBER",
                "VIEW_MEMBERS",
                "MARK_ATTENDANCE"
        );

        log.info("✅ Gym role-permission mappings initialized.");
    }

    private void assignPermissions(String roleCode, String... permissionCodes) {

        GymRole role = gymRoleRepository.findByRoleCode(roleCode)
                        .orElse(null);

        if (role == null) {

            log.error("❌ Role not found: {}", roleCode);

            return;
        }

        if (role.getPermissions() == null) {
            role.setPermissions(new HashSet<>());
        }

        for (String permissionCode : permissionCodes) {

            GymPermission permission =
                    gymPermissionRepository.findByPermissionCode(permissionCode)
                            .orElse(null);

            if (permission == null) {

                log.error("❌ Permission [{}] not found for role [{}]", permissionCode, roleCode);

                continue;
            }

            boolean alreadyAssigned =
                    role.getPermissions()
                            .stream()
                            .anyMatch(p ->
                                    p.getPermissionCode()
                                            .equals(permissionCode)
                            );

            if (alreadyAssigned) {

                log.info("⏩ Permission [{}] already assigned to role [{}]", permissionCode, roleCode);

                continue;
            }

            role.getPermissions().add(permission);

            log.info("🔗 Assigned Permission [{}] to Role [{}]", permissionCode, roleCode);
        }

        gymRoleRepository.save(role);
    }
}
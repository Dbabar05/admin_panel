package com.gym.config;

import com.gym.entity.AdminPermission;
import com.gym.entity.AdminRole;
import com.gym.entity.AdminUser;
import com.gym.repository.AdminPermissionRepository;
import com.gym.repository.AdminRoleRepository;
import com.gym.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRoleRepository roleRepository;
    private final AdminPermissionRepository permissionRepository;
    private final AdminUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        log.info("🔁 Running DataInitializer...");

        boolean alreadyInitialized = roleRepository.findByName("SUPER_ADMIN").isPresent();

        if (alreadyInitialized) {
            log.info("✅ Data already initialized. Skipping...");
            return;
        }

        log.info("🚀 Initializing default roles, permissions, and admin user...");

        AdminPermission createAdmin = createPermission("CREATE_ADMIN_USER");
        AdminPermission manageRoles = createPermission("MANAGE_ROLES");
        AdminPermission managePermissions = createPermission("MANAGE_PERMISSIONS");

        AdminRole superAdminRole = AdminRole.builder()
                .name("SUPER_ADMIN")
                .permissions(Set.of(createAdmin, manageRoles, managePermissions))
                .build();

        roleRepository.save(superAdminRole);

        AdminUser admin = AdminUser.builder()
                .name("Super Admin")
                .email("admin@gym.com")
                .password(passwordEncoder.encode("Admin@123"))
                .role(superAdminRole)
                .build();

        userRepository.save(admin);

        log.info("🎉 Default SUPER_ADMIN user created successfully!");
    }

    private AdminPermission createPermission(String name) {
        return permissionRepository.save(
                AdminPermission.builder()
                        .name(name)
                        .build()
        );
    }
}
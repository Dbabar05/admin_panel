package com.gym.repository;

import com.gym.entity.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminPermissionRepository extends JpaRepository<AdminPermission, UUID> {
    Optional<AdminPermission> findByName(String name);
}

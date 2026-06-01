package com.gym.repository;

import com.gym.entity.GymPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GymPermissionRepository extends JpaRepository<GymPermission, UUID> {

    Optional<GymPermission> findByPermissionCode(String permissionCode);
}

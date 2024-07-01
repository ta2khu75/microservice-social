package dev.ta2khu74.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.ta2khu74.identity.model.Permission;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>{}
package com.example.appspringhomework251.repository;

import com.example.appspringhomework251.entity.Role;
import com.example.appspringhomework251.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName(RoleName roleName);
}

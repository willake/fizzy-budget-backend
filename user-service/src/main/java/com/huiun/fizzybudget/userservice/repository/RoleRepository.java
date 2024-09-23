package com.huiun.fizzybudget.userservice.repository;

import com.huiun.fizzybudget.sharedentities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleId(Long roleId);

    Optional<Role> findByRoleName(String roleName);
}

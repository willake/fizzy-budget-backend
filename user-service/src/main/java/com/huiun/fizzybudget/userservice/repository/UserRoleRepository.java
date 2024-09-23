package com.huiun.fizzybudget.userservice.repository;

import com.huiun.fizzybudget.sharedentities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}

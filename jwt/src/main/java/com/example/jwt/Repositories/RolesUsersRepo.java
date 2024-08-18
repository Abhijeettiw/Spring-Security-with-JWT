package com.example.jwt.Repositories;

import com.example.jwt.Entities.RoleUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesUsersRepo extends JpaRepository<RoleUsers,Long> {
    public List<RoleUsers> findAllByAppUserId(Long appUserId);
    public List<RoleUsers> findAllByRoleId(Long roleId);
}

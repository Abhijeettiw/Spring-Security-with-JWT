package com.example.jwt.Repositories;

import com.example.jwt.Entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRolesRepo extends JpaRepository<UserRoles,Long> {
}

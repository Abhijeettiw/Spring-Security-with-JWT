package com.example.jwt.Repositories;

import com.example.jwt.Entities.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AppUserRepo extends JpaRepository<AppUsers,Long> {
    public Optional<AppUsers> findByUsername(String username);
}

package com.example.jwt.Repositories;

import com.example.jwt.Entities.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorsRepo extends JpaRepository<Visitors,Long> {
}

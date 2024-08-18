package com.example.jwt.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles_users")
@Entity
public class RoleUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "app_User_id")
    private Long appUserId;
    @Column(name = "role_id")
    private Long roleId;
}

package com.example.jwt.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "visitors")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Visitors {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String contactNo;
    private String email;
    @Lob
    private String reason;

}

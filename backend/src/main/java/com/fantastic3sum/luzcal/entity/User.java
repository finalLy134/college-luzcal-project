package com.fantastic3sum.luzcal.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "users")
@Getter 
@Setter 
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = true)
    private String passwordHash;

    @Column(nullable = true)
    private String googleId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

}

package com.cabeleleira.leila.salao.domain;

import com.cabeleleira.leila.salao.dto.CreateUserRequestDTO;
import com.cabeleleira.leila.salao.enums.UsersRole;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public final class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_bcrypt", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UsersRole role;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    public User() {
    }

    public User(UUID id, String email, String password, UsersRole role, Boolean active, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
    }

    public static User from(CreateUserRequestDTO dto, BCryptPasswordEncoder passwordEncoder) {
        return new User(
                null,
                dto.email(),
                passwordEncoder.encode(dto.password()),
                UsersRole.CLIENT,
                true,
                Instant.now()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsersRole getRole() {
        return role;
    }

    public void setRole(UsersRole role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

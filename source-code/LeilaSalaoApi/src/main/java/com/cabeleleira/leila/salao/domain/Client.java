package com.cabeleleira.leila.salao.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
        name = "clients",
        indexes = {
                @Index(name = "idx_user_name", columnList = "name"),
                @Index(name = "idx_user_phone_number", columnList = "phone_number")
        }
)
public final class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @Column(length = 120, nullable = false)
    private String name;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    public Client() {
    }

    public Client(UUID id, User user, String name, String phoneNumber, Instant createdAt) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

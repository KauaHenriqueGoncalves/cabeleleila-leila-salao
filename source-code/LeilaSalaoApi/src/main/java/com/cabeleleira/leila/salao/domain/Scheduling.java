package com.cabeleleira.leila.salao.domain;

import com.cabeleleira.leila.salao.dto.CreateSchedulingRequestDTO;
import com.cabeleleira.leila.salao.enums.SchedulingOrigin;
import com.cabeleleira.leila.salao.enums.SchedulingStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
        name = "scheduling",
        indexes = {
                @Index(name = "idx_scheduling_client_id", columnList = "client_id"),
                @Index(name = "idx_scheduling_date_hours", columnList = "date_hours"),
                @Index(name = "idx_scheduling_status", columnList = "status")
        }
)
public final class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "date_hours", nullable = false)
    private LocalDateTime dateHours;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchedulingStatus status;

    @Column(length = 250)
    private String observations;

    @Enumerated(EnumType.STRING)
    private SchedulingOrigin origin;

    @Column(name = "price_charged", nullable = false)
    private Double priceCharged;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "scheduling_services",
            joinColumns = @JoinColumn(name = "scheduling_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "services_id", nullable = false),
            indexes = {
                    @Index(name = "idx_scheduling_services_scheduling_id", columnList = "scheduling_id"),
                    @Index(name = "idx_scheduling_services_services_id", columnList = "services_id"),
                    @Index(name = "idx_scheduling_services_scheduling_id_services_id", columnList = "scheduling_id, services_id")
            }
    )
    private List<ServiceDomain> serviceDomains = new ArrayList<>();

    @OneToMany(mappedBy = "scheduling", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoryChanges> historyChanges = new ArrayList<>();

    public Scheduling(){
    }

    public Scheduling(UUID id, Client client, LocalDateTime dateHours, SchedulingStatus status, String observations, SchedulingOrigin origin, Double priceCharged, Instant createdAt, Instant updatedAt, List<ServiceDomain> serviceDomains) {
        this.id = id;
        this.client = client;
        this.dateHours = dateHours;
        this.status = status;
        this.observations = observations;
        this.origin = origin;
        this.priceCharged = priceCharged;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.serviceDomains = serviceDomains;
    }

    public static Scheduling from(Client client, List<ServiceDomain> services, double finalPrice, CreateSchedulingRequestDTO dto) {
        return new Scheduling(
                null,
                client,
                dto.dateHours(),
                SchedulingStatus.SCHEDULED,
                dto.observations(),
                dto.origin(),
                finalPrice,
                Instant.now(),
                null,
                services
        );
    }

    public static Scheduling copy(Scheduling s) {
        return new Scheduling(
                s.getId(),
                s.getClient(),
                s.getDateHours(),
                s.getStatus(),
                s.getObservations(),
                s.getOrigin(),
                s.getPriceCharged(),
                s.getCreatedAt(),
                s.getUpdatedAt(),
                s.getServices()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDateHours() {
        return dateHours;
    }

    public void setDateHours(LocalDateTime dateHours) {
        this.dateHours = dateHours;
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulingStatus status) {
        this.status = status;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public SchedulingOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(SchedulingOrigin origin) {
        this.origin = origin;
    }

    public Double getPriceCharged() {
        return priceCharged;
    }

    public void setPriceCharged(Double priceCharged) {
        this.priceCharged = priceCharged;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ServiceDomain> getServices() {
        return serviceDomains;
    }

    public void setServices(List<ServiceDomain> serviceDomains) {
        this.serviceDomains = serviceDomains;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Scheduling that = (Scheduling) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

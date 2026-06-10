package com.cabeleleira.leila.salao.domain;

import com.cabeleleira.leila.salao.enums.HistoryChangedFor;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "history_changes")
public final class HistoryChanges {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduling_id", nullable = false)
    private Scheduling scheduling;

    @Enumerated(EnumType.STRING)
    @Column(name = "changed_for")
    private HistoryChangedFor changedFor;

    @Column(name = "field_changed", length = 100, nullable = false)
    private String fieldChanged;

    @Column(name = "price_before")
    private Double priceBefore;

    @Column(name = "price_now")
    private Double priceNow;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    public HistoryChanges() {
    }

    public HistoryChanges(UUID id, Scheduling scheduling, HistoryChangedFor changedFor, String fieldChanged, Double priceBefore, Double priceNow, Instant createdAt) {
        this.id = id;
        this.scheduling = scheduling;
        this.changedFor = changedFor;
        this.fieldChanged = fieldChanged;
        this.priceBefore = priceBefore;
        this.priceNow = priceNow;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }

    public HistoryChangedFor getChangedFor() {
        return changedFor;
    }

    public void setChangedFor(HistoryChangedFor changedFor) {
        this.changedFor = changedFor;
    }

    public String getFieldChanged() {
        return fieldChanged;
    }

    public void setFieldChanged(String fieldChanged) {
        this.fieldChanged = fieldChanged;
    }

    public Double getPriceBefore() {
        return priceBefore;
    }

    public void setPriceBefore(Double priceBefore) {
        this.priceBefore = priceBefore;
    }

    public Double getPriceNow() {
        return priceNow;
    }

    public void setPriceNow(Double priceNow) {
        this.priceNow = priceNow;
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
        HistoryChanges that = (HistoryChanges) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

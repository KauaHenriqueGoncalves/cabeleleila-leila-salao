package com.cabeleleira.leila.salao.domain;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "operating_hours")
public final class OperationHours {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "day_week", nullable = false)
    private Short dayWeek; // 0=dom ... 6=sab

    @Column(name = "opening_hours", nullable = false)
    private LocalTime openingHours;

    @Column(name = "closing_hours", nullable = false)
    private LocalTime closingHours;

    @Column(name = "active", nullable = false)
    private Boolean active;

    public OperationHours() {
    }

    public OperationHours(UUID id, Short dayWeek, LocalTime openingHours, LocalTime closingHours, Boolean active) {
        this.id = id;
        this.dayWeek = dayWeek;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Short getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(Short dayWeek) {
        this.dayWeek = dayWeek;
    }

    public LocalTime getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(LocalTime closingHours) {
        this.closingHours = closingHours;
    }

    public LocalTime getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OperationHours that = (OperationHours) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

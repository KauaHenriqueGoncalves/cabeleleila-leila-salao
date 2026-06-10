package com.cabeleleira.leila.salao.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "schedule_blocks")
public final class ScheduleBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date_initial", nullable = false)
    private LocalDate dateInitial;

    @Column(name = "date_end", nullable = false)
    private LocalDate dateEnd;

    private String reason;

    public ScheduleBlock() {
    }

    public ScheduleBlock(UUID id, LocalDate dateInitial, LocalDate dateEnd, String reason) {
        this.id = id;
        this.dateInitial = dateInitial;
        this.dateEnd = dateEnd;
        this.reason = reason;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDateInitial() {
        return dateInitial;
    }

    public void setDateInitial(LocalDate dateInitial) {
        this.dateInitial = dateInitial;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleBlock that = (ScheduleBlock) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

package com.cabeleleira.leila.salao.repository;

import com.cabeleleira.leila.salao.domain.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SchedulingRepository extends JpaRepository<Scheduling, UUID> {
    List<Scheduling> findAllByClientId(UUID clientId);
}

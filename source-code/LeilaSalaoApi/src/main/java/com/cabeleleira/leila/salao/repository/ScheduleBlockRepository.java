package com.cabeleleira.leila.salao.repository;

import com.cabeleleira.leila.salao.domain.ScheduleBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleBlockRepository extends JpaRepository<ScheduleBlock, UUID> {
}

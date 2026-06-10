package com.cabeleleira.leila.salao.repository;

import com.cabeleleira.leila.salao.domain.HistoryChanges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoryChangesRepository extends JpaRepository<HistoryChanges, UUID> {
}

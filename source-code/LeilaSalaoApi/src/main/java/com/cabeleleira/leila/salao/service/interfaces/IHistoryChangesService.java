package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.HistoryChanges;
import com.cabeleleira.leila.salao.domain.Scheduling;
import com.cabeleleira.leila.salao.dto.HistoryChangesToListResponseDTO;
import com.cabeleleira.leila.salao.enums.HistoryChangedFor;

import java.util.List;
import java.util.UUID;

public interface IHistoryChangesService {
    List<HistoryChangesToListResponseDTO> findAllBySchedulingId(UUID id);
    HistoryChanges findById(UUID id);
    UUID create(Scheduling before, Scheduling after, HistoryChangedFor changedFor);
}

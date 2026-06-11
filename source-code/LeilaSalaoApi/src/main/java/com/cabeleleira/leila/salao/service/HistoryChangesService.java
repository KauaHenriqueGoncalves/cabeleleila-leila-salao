package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.HistoryChanges;
import com.cabeleleira.leila.salao.domain.Scheduling;
import com.cabeleleira.leila.salao.dto.HistoryChangesToListResponseDTO;
import com.cabeleleira.leila.salao.enums.HistoryChangedFor;
import com.cabeleleira.leila.salao.repository.HistoryChangesRepository;
import com.cabeleleira.leila.salao.service.interfaces.IHistoryChangesService;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class HistoryChangesService implements IHistoryChangesService {
    private final static Logger log = LoggerFactory.getLogger(HistoryChangesService.class);

    private final HistoryChangesRepository historyChangesRepository;

    public HistoryChangesService(HistoryChangesRepository historyChangesRepository) {
        this.historyChangesRepository = historyChangesRepository;
    }

    @Override
    public List<HistoryChangesToListResponseDTO> findAllBySchedulingId(UUID id) {
        return historyChangesRepository.findAllBySchedulingId(id)
                .stream()
                .sorted(Comparator.comparing(HistoryChanges::getCreatedAt))
                .map(HistoryChangesToListResponseDTO::from)
                .toList();
    }

    @Override
    public HistoryChanges findById(UUID id) {
        return historyChangesRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("não encontrou o historico de mudanças");
                });
    }

    @Override
    @Transactional
    public UUID create(Scheduling before, Scheduling after, HistoryChangedFor changedFor) {
        String fieldsChanged = getDifferentiesFields(before, after);
        HistoryChanges changes = new HistoryChanges(
                null,
                after,
                changedFor,
                fieldsChanged,
                before.getPriceCharged(),
                after.getPriceCharged(),
                Instant.now()
        );
        return historyChangesRepository.save(changes).getId();
    }

    private String getDifferentiesFields(Scheduling before, Scheduling after) {
        ArrayList<String> fields = new ArrayList<>();

        if (!before.getDateHours().equals(after.getDateHours())) {
            fields.add("data_hours");
        }

        if (!before.getStatus().equals(after.getStatus())) {
            fields.add("status");
        }

        if (!before.getOrigin().equals(after.getOrigin())) {
            fields.add("origin");
        }

        if (!before.getServices().equals(after.getServices())) {
            fields.add("services");
        }

        return String.join(" | ", fields);
    }

}

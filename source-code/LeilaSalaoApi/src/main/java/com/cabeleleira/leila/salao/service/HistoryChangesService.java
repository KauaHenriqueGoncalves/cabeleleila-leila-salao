package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.HistoryChanges;
import com.cabeleleira.leila.salao.repository.HistoryChangesRepository;
import com.cabeleleira.leila.salao.service.interfaces.IHistoryChangesService;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HistoryChangesService implements IHistoryChangesService {
    private final static Logger log = LoggerFactory.getLogger(HistoryChangesService.class);

    private final HistoryChangesRepository historyChangesRepository;

    public HistoryChangesService(HistoryChangesRepository historyChangesRepository) {
        this.historyChangesRepository = historyChangesRepository;
    }

    @Override
    public HistoryChanges findById(UUID id) {
        return historyChangesRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("não encontrou o historico de mudanças");
                });
    }
}

package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.Scheduling;
import com.cabeleleira.leila.salao.repository.SchedulingRepository;
import com.cabeleleira.leila.salao.service.interfaces.ISchedulingService;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SchedulingService implements ISchedulingService {
    private static final Logger log = LoggerFactory.getLogger(SchedulingService.class);

    private final SchedulingRepository schedulingRepository;

    public SchedulingService(SchedulingRepository schedulingRepository) {
        this.schedulingRepository = schedulingRepository;
    }

    @Override
    public Scheduling findById(UUID id) {
        return schedulingRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("Não encontrou o agendamento do salão");
                });
    }
}

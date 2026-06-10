package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.ScheduleBlock;
import com.cabeleleira.leila.salao.repository.ScheduleBlockRepository;
import com.cabeleleira.leila.salao.service.interfaces.IScheduleBlockService;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ScheduleBlockService implements IScheduleBlockService {
    private final static Logger log = LoggerFactory.getLogger(ScheduleBlockService.class);

    private final ScheduleBlockRepository scheduleBlockRepository;

    public ScheduleBlockService(ScheduleBlockRepository scheduleBlockRepository) {
        this.scheduleBlockRepository = scheduleBlockRepository;
    }

    @Override
    public ScheduleBlock findById(UUID id) {
        return scheduleBlockRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("Não encontrou o bloqueio do agendamento");
                });
    }
}

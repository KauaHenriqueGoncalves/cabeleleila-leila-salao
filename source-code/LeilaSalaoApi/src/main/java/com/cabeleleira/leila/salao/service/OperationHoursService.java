package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.OperationHours;
import com.cabeleleira.leila.salao.repository.OperationHoursRepository;
import com.cabeleleira.leila.salao.service.interfaces.IOperationHoursService;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OperationHoursService implements IOperationHoursService {
    private final static Logger log = LoggerFactory.getLogger(OperationHoursService.class);

    private final OperationHoursRepository operationHoursRepository;

    public OperationHoursService(OperationHoursRepository operationHoursRepository) {
        this.operationHoursRepository = operationHoursRepository;
    }

    @Override
    public OperationHours findById(UUID id) {
        return operationHoursRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("Não encontrou as horas de operação");
                });
    }
}

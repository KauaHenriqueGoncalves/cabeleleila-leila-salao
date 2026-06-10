package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.OperationHours;

import java.util.UUID;

public interface IOperationHoursService {
    OperationHours findById(UUID id);
}

package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.Scheduling;

import java.util.UUID;

public interface ISchedulingService {
    Scheduling findById(UUID id);
}

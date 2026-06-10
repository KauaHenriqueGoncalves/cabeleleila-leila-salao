package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.ScheduleBlock;

import java.util.UUID;

public interface IScheduleBlockService {
    ScheduleBlock findById(UUID id);
}

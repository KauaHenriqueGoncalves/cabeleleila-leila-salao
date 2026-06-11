package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.Scheduling;
import com.cabeleleira.leila.salao.dto.CreateSchedulingRequestDTO;
import com.cabeleleira.leila.salao.dto.SchedulingToListResponseDTO;
import com.cabeleleira.leila.salao.dto.UpdateSchedulingAdminRequestDTO;
import com.cabeleleira.leila.salao.dto.UpdateSchedulingClientRequestDTO;

import java.util.List;
import java.util.UUID;

public interface ISchedulingService {
    List<SchedulingToListResponseDTO> findAll();
    List<SchedulingToListResponseDTO> findAllByUser(UUID userId);
    Scheduling findById(UUID id);
    UUID create(CreateSchedulingRequestDTO dto, UUID userId);
    void updateClient(UUID scheduledId, UpdateSchedulingClientRequestDTO dto);
    void updateAdmin(UUID scheduledId, UpdateSchedulingAdminRequestDTO dto);
    void deleteById(UUID id);
}

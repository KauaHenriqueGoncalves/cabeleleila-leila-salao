package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.ServiceDomain;
import com.cabeleleira.leila.salao.dto.CreateServiceRequestDTO;
import com.cabeleleira.leila.salao.dto.ServiceToListResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IServiceDomainService {
    List<ServiceToListResponseDTO> findAll();
    ServiceDomain findById(UUID id);
    UUID create(CreateServiceRequestDTO dto);
    void deleteById(UUID id);
}

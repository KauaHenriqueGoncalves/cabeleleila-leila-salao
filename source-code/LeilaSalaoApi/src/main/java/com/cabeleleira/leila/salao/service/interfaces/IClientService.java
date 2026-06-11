package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.Client;
import com.cabeleleira.leila.salao.dto.ClientToListResponseDTO;
import com.cabeleleira.leila.salao.dto.CreateClientRequestDTO;

import java.util.List;
import java.util.UUID;

public interface IClientService {
    List<ClientToListResponseDTO> findAll();
    Client findById(UUID id);
    Client findByUserId(UUID id);
    UUID create(CreateClientRequestDTO dto);
    void deleteById(UUID id);
}

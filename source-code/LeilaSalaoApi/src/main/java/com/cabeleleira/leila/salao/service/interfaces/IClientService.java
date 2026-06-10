package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.Client;
import com.cabeleleira.leila.salao.dto.CreateClientRequestDTO;

import java.util.UUID;

public interface IClientService {
    Client findById(UUID id);
    UUID create(CreateClientRequestDTO dto);
}

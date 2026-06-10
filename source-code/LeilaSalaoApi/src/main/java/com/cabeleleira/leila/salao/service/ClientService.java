package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.Client;
import com.cabeleleira.leila.salao.domain.User;
import com.cabeleleira.leila.salao.dto.CreateClientRequestDTO;
import com.cabeleleira.leila.salao.repository.ClientRepository;
import com.cabeleleira.leila.salao.service.interfaces.IClientService;
import com.cabeleleira.leila.salao.service.interfaces.IUserService;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService implements IClientService {
    private final static Logger log = LoggerFactory.getLogger(ClientService.class);

    private final IUserService userService;
    private final ClientRepository clientRepository;

    public ClientService(IUserService userService, ClientRepository clientRepository) {
        this.userService = userService;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client findById(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("Não encontrou o cliente");
                });
    }

    @Override
    @Transactional
    public UUID create(CreateClientRequestDTO dto) {
        User user = userService.createRoleClient(dto.user());
        Client client = Client.from(user, dto);
        return clientRepository.save(client).getId();
    }
}

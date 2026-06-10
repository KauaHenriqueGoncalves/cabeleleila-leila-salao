package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.User;
import com.cabeleleira.leila.salao.dto.CreateUserRequestDTO;

import java.util.UUID;

public interface IUserService {
    User findById(UUID id);
    User createRoleClient(CreateUserRequestDTO dto);
}

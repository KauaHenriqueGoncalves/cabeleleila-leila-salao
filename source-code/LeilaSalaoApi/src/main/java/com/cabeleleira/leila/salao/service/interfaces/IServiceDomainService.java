package com.cabeleleira.leila.salao.service.interfaces;

import com.cabeleleira.leila.salao.domain.ServiceDomain;

import java.util.UUID;

public interface IServiceDomainService {
    ServiceDomain findById(UUID id);
}

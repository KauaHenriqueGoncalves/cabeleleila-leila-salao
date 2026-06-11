package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.ServiceDomain;
import com.cabeleleira.leila.salao.dto.CreateServiceRequestDTO;
import com.cabeleleira.leila.salao.dto.ServiceToListResponseDTO;
import com.cabeleleira.leila.salao.repository.ServiceDomainRepository;
import com.cabeleleira.leila.salao.service.interfaces.IServiceDomainService;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceDomainService implements IServiceDomainService {
    private final static Logger log = LoggerFactory.getLogger(ServiceDomainService.class);

    private final ServiceDomainRepository serviceDomainRepository;

    public ServiceDomainService(ServiceDomainRepository serviceDomainRepository) {
        this.serviceDomainRepository = serviceDomainRepository;
    }

    @Override
    public List<ServiceToListResponseDTO> findAll() {
        return serviceDomainRepository.findAll()
                .stream()
                .map(ServiceToListResponseDTO::from)
                .toList();
    }

    @Override
    public ServiceDomain findById(UUID id) {
        return serviceDomainRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("Não encontrou o serviços do salão");
                });
    }

    @Override
    public UUID create(CreateServiceRequestDTO dto) {
        ServiceDomain service = ServiceDomain.from(dto);
        return serviceDomainRepository.save(service).getId();
    }

    @Override
    public void deleteById(UUID id) {
        ServiceDomain s = findById(id);
        serviceDomainRepository.deleteById(id);
    }
}

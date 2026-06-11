package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.Client;
import com.cabeleleira.leila.salao.domain.Scheduling;
import com.cabeleleira.leila.salao.domain.ServiceDomain;
import com.cabeleleira.leila.salao.dto.CreateSchedulingRequestDTO;
import com.cabeleleira.leila.salao.dto.SchedulingToListResponseDTO;
import com.cabeleleira.leila.salao.dto.UpdateSchedulingAdminRequestDTO;
import com.cabeleleira.leila.salao.dto.UpdateSchedulingClientRequestDTO;
import com.cabeleleira.leila.salao.repository.SchedulingRepository;
import com.cabeleleira.leila.salao.service.interfaces.IClientService;
import com.cabeleleira.leila.salao.service.interfaces.ISchedulingService;
import com.cabeleleira.leila.salao.service.interfaces.IServiceDomainService;
import com.cabeleleira.leila.salao.shared.exceptions.BusinessException;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class SchedulingService implements ISchedulingService {
    private static final Logger log = LoggerFactory.getLogger(SchedulingService.class);

    private final IClientService clientService;
    private final IServiceDomainService serviceDomainService;
    private final SchedulingRepository schedulingRepository;

    public SchedulingService(IClientService clientService, IServiceDomainService serviceDomainService, SchedulingRepository schedulingRepository) {
        this.clientService = clientService;
        this.serviceDomainService = serviceDomainService;
        this.schedulingRepository = schedulingRepository;
    }

    @Override
    public List<SchedulingToListResponseDTO> findAll() {
        return schedulingRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Scheduling::getCreatedAt))
                .map(SchedulingToListResponseDTO::from)
                .toList();
    }

    @Override
    public List<SchedulingToListResponseDTO> findAllByUser(UUID userId) {
        Client client = clientService.findByUserId(userId);
        return schedulingRepository.findAllByClientId(client.getId())
                .stream()
                .sorted(Comparator.comparing(Scheduling::getCreatedAt))
                .map(SchedulingToListResponseDTO::from)
                .toList();
    }

    @Override
    public Scheduling findById(UUID id) {
        return schedulingRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("Não encontrou o agendamento do salão");
                });
    }

    @Override
    @Transactional
    public UUID create(CreateSchedulingRequestDTO dto, UUID userId) {
        Client client = clientService.findByUserId(userId);
        List<ServiceDomain> services = new ArrayList<>();
        for (UUID id : dto.servicesIds()) {
            ServiceDomain s = serviceDomainService.findById(id);
            services.add(s);
        }
        double finalPrice = services.stream()
                .mapToDouble(ServiceDomain::getPrice)
                .sum();
        Scheduling scheduling = Scheduling.from(client, services, finalPrice, dto);
        return schedulingRepository.save(scheduling).getId();
    }

    @Override
    @Transactional
    public void updateClient(UUID scheduledId, UpdateSchedulingClientRequestDTO dto) {
        Scheduling s = findById(scheduledId);
        if (LocalDateTime.now().plus(Duration.ofDays(2)).isAfter(s.getDateHours())) {
            throw new BusinessException("Não é possivel fazer alteração faltando 2 dias, faça a alteração pelo telefone");
        }
        List<ServiceDomain> services = new ArrayList<>();
        for (UUID id : dto.servicesIds()) {
            ServiceDomain se = serviceDomainService.findById(id);
            services.add(se);
        }
        double finalPrice = services.stream()
                .mapToDouble(ServiceDomain::getPrice)
                .sum();
        s.setOrigin(dto.origin());
        s.setPriceCharged(finalPrice);
        s.setServices(services);
        s.setUpdatedAt(Instant.now());
    }

    @Override
    @Transactional
    public void updateAdmin(UUID scheduledId, UpdateSchedulingAdminRequestDTO dto) {
        Scheduling s = findById(scheduledId);
        List<ServiceDomain> services = new ArrayList<>();
        for (UUID id : dto.servicesIds()) {
            ServiceDomain se = serviceDomainService.findById(id);
            services.add(se);
        }
        double finalPrice = services.stream()
                .mapToDouble(ServiceDomain::getPrice)
                .sum();
        s.setStatus(dto.status());
        s.setDateHours(dto.dateHours());
        s.setOrigin(dto.origin());
        s.setPriceCharged(finalPrice);
        s.setServices(services);
        s.setUpdatedAt(Instant.now());
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        Scheduling s = findById(id);
        schedulingRepository.deleteById(id);
    }
}

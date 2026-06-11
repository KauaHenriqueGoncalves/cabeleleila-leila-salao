package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.dto.DashboardDTO;
import com.cabeleleira.leila.salao.enums.SchedulingStatus;
import com.cabeleleira.leila.salao.repository.ClientRepository;
import com.cabeleleira.leila.salao.repository.SchedulingRepository;
import com.cabeleleira.leila.salao.service.interfaces.IDashboardService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardService implements IDashboardService {
    private final SchedulingRepository schedulingRepository;
    private final ClientRepository clientRepository;

    public DashboardService(SchedulingRepository schedulingRepository, ClientRepository clientRepository) {
        this.schedulingRepository = schedulingRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public DashboardDTO getDashboard() {
        LocalDateTime start = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime end = LocalDate.now().with(DayOfWeek.SUNDAY).atTime(23, 59, 59);
        return new DashboardDTO(
                schedulingRepository.countByPeriod(start, end),
                schedulingRepository.countByPeriodAndStatus(start, end, SchedulingStatus.DONE),
                schedulingRepository.countByPeriodAndStatus(start, end, SchedulingStatus.CANCELED),
                schedulingRepository.countByPeriodAndStatus(start, end, SchedulingStatus.SCHEDULED),
                schedulingRepository.sumRevenue(start, end),
                clientRepository.count()
        );
    }
}

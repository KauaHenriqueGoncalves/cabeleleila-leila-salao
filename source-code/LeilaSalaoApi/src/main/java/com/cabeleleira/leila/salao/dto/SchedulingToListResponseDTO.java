package com.cabeleleira.leila.salao.dto;

import com.cabeleleira.leila.salao.domain.Scheduling;
import com.cabeleleira.leila.salao.enums.SchedulingOrigin;
import com.cabeleleira.leila.salao.enums.SchedulingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SchedulingToListResponseDTO(
        UUID id,
        String nameClient,
        String phoneNumberClient,
        LocalDateTime dateHours,
        SchedulingStatus status,
        String observations,
        SchedulingOrigin origin,
        Double priceCharged,
        List<ServiceToSchedulingDTO> services
) {

    record ServiceToSchedulingDTO(
            UUID id,
            String name
    ) { }

    public static SchedulingToListResponseDTO from(Scheduling s) {
        return new SchedulingToListResponseDTO(
                s.getId(),
                s.getClient().getName(),
                s.getClient().getPhoneNumber(),
                s.getDateHours(),
                s.getStatus(),
                s.getObservations(),
                s.getOrigin(),
                s.getPriceCharged(),
                s.getServices()
                        .stream()
                        .map(se -> new ServiceToSchedulingDTO(se.getId(), se.getName()))
                        .toList()
        );
    }
}

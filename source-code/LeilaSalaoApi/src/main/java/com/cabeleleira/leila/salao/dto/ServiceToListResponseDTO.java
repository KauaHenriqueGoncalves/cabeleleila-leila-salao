package com.cabeleleira.leila.salao.dto;

import com.cabeleleira.leila.salao.domain.ServiceDomain;

import java.util.UUID;

public record ServiceToListResponseDTO(
        UUID id,
        String name,
        String description,
        Integer durationMinutes,
        Double price
) {
    public static ServiceToListResponseDTO from(ServiceDomain s) {
        return new ServiceToListResponseDTO(
                s.getId(),
                s.getName(),
                s.getDescription(),
                s.getDurationMinutes(),
                s.getPrice()
        );
    }
}

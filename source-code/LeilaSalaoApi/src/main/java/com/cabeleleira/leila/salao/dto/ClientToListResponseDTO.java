package com.cabeleleira.leila.salao.dto;

import com.cabeleleira.leila.salao.domain.Client;

import java.util.UUID;

public record ClientToListResponseDTO(
        UUID id,
        String email,
        String name,
        String phoneNumber
) {
    public static ClientToListResponseDTO from(Client c) {
        return new ClientToListResponseDTO(
                c.getId(),
                c.getUser().getEmail(),
                c.getName(),
                c.getPhoneNumber()
        );
    }
}

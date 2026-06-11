package com.cabeleleira.leila.salao.auth.dto;

public record TokenResponseDTO(
        String accessToken,
        String role
) { }

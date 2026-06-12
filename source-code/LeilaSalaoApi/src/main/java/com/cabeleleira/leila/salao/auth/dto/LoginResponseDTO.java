package com.cabeleleira.leila.salao.auth.dto;

import java.util.UUID;

public record LoginResponseDTO(
    UUID id,
    String role
) { }

package com.cabeleleira.leila.salao.auth.dto;

import java.util.UUID;

public record LoginResponse(
    UUID id,
    String role
) { }

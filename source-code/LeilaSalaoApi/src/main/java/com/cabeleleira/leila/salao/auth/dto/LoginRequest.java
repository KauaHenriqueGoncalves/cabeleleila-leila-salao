package com.cabeleleira.leila.salao.auth.dto;

public record LoginRequest(
        String email,
        String password
) { }

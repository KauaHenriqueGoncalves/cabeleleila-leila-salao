package com.cabeleleira.leila.salao.auth.dto;

public record AdminLoginRequest(
        String name,
        String password
) { }

package com.cabeleleira.leila.salao.auth.service;

import com.cabeleleira.leila.salao.auth.dto.LoginResponse;
import org.springframework.security.oauth2.jwt.Jwt;

public interface IJwtService {
    String generateAccessToken(LoginResponse loginResponse);
    Jwt decode(String token);
}

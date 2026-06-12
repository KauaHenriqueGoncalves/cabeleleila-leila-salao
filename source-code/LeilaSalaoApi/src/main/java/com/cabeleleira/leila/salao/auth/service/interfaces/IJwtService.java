package com.cabeleleira.leila.salao.auth.service.interfaces;

import com.cabeleleira.leila.salao.auth.dto.LoginResponseDTO;
import org.springframework.security.oauth2.jwt.Jwt;

public interface IJwtService {
    String generateAccessToken(LoginResponseDTO loginResponseDTO);
    Jwt decode(String token);
}

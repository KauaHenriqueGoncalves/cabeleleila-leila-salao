package com.cabeleleira.leila.salao.auth.controller;

import com.cabeleleira.leila.salao.auth.dto.LoginRequestDTO;
import com.cabeleleira.leila.salao.auth.dto.LoginResponseDTO;
import com.cabeleleira.leila.salao.auth.dto.TokenResponseDTO;
import com.cabeleleira.leila.salao.auth.service.interfaces.IJwtService;
import com.cabeleleira.leila.salao.auth.service.interfaces.ILoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ILoginService loginService;
    private final IJwtService jwtService;

    public AuthController(ILoginService loginService, IJwtService jwtService) {
        this.loginService = loginService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<TokenResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequest
    ) {
        LoginResponseDTO loginResponse = loginService.login(loginRequest);
        String accessToken = jwtService.generateAccessToken(loginResponse);
        TokenResponseDTO token = new TokenResponseDTO(accessToken, loginResponse.role());
        return ResponseEntity.ok(token);
    }
}

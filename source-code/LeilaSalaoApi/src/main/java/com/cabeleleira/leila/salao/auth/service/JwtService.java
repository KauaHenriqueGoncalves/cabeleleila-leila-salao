package com.cabeleleira.leila.salao.auth.service;

import com.cabeleleira.leila.salao.auth.dto.LoginResponse;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class JwtService implements IJwtService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtService(
            JwtEncoder jwtEncoder,
            JwtDecoder jwtDecoder
    ) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public String generateAccessToken(LoginResponse loginResponse) {
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("leila-salao-api")
                .audience(List.of("system-web", "leila-api"))
                .issuedAt(now)
                .expiresAt(now.plus(Duration.ofHours(10))) // Não irei fazer refresh token
                .subject(loginResponse.id().toString())
                .claims(claims -> {
                    claims.put(
                            "role",
                            loginResponse.role()
                    );
                    claims.put("type", "access_token");
                })
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    @Override
    public Jwt decode(String token) {
        return jwtDecoder.decode(token);
    }
}

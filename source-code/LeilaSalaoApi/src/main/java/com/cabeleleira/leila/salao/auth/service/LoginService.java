package com.cabeleleira.leila.salao.auth.service;

import com.cabeleleira.leila.salao.auth.dto.LoginRequestDTO;
import com.cabeleleira.leila.salao.auth.dto.LoginResponseDTO;
import com.cabeleleira.leila.salao.auth.service.interfaces.ILoginService;
import com.cabeleleira.leila.salao.domain.User;
import com.cabeleleira.leila.salao.service.interfaces.IUserService;
import com.cabeleleira.leila.salao.shared.exceptions.AccessDeniedException;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {
    private final static Logger log = LoggerFactory.getLogger(LoginService.class);

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginService(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user;
        try {
            user = userService.findByEmail(dto.email());
        }
        catch (NotFoundObjectException e) {
            throw new AccessDeniedException("Credenciais Invalidas");
        }
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new AccessDeniedException("Credenciais Invalidas");
        }
        return new LoginResponseDTO(user.getId(), user.getRole().getName());
    }
}

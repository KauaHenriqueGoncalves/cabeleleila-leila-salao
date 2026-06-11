package com.cabeleleira.leila.salao.auth.service.interfaces;

import com.cabeleleira.leila.salao.auth.dto.LoginRequestDTO;
import com.cabeleleira.leila.salao.auth.dto.LoginResponseDTO;

public interface ILoginService {
    LoginResponseDTO login(LoginRequestDTO dto);
}

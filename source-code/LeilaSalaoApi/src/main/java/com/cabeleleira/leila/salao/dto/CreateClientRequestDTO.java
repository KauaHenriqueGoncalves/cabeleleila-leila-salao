package com.cabeleleira.leila.salao.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClientRequestDTO(
        @Valid
        CreateUserRequestDTO user,

        @NotBlank(message = "O nome do cliente é obrigatório")
        @Size(min = 3, max = 100, message = "O nome do cliente deve ter entre 3 e 100 caracteres")
        String name,

        @NotBlank(message = "O número de telefone é obrigatório")
        @Size(min = 10, max = 15, message = "O número de telefone deve ter entre 10 e 15 caracteres")
        String phoneNumber
) { }

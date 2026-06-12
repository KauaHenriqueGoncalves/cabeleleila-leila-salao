package com.cabeleleira.leila.salao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateServiceRequestDTO(
        @NotBlank(message = "O nome do serviço é obrigatório")
        @Size(min = 3, max = 100, message = "O nome do serviço deve ter entre 3 e 100 caracteres")
        String name,

        @Size(max = 250, message = "A descrição deve ter no máximo 250 caracteres")
        String description,

        @NotNull(message = "A duração em minutos é obrigatória")
        @Positive(message = "A duração em minutos deve ser um valor positivo")
        Integer durationMinutes,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser um valor positivo")
        Double price
) { }

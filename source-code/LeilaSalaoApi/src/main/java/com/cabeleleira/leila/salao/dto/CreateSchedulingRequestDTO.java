package com.cabeleleira.leila.salao.dto;

import com.cabeleleira.leila.salao.enums.SchedulingOrigin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateSchedulingRequestDTO(
        @NotNull(message = "A data e hora do agendamento é obrigatória")
        @Future(message = "A data do agendamento deve ser no futuro")
        LocalDateTime dateHours,

        @Size(max = 500, message = "As observações devem ter no máximo 500 caracteres")
        String observations,

        SchedulingOrigin origin,

        @NotEmpty(message = "É necessário informar pelo menos um serviço")
        List<UUID> servicesIds
) { }

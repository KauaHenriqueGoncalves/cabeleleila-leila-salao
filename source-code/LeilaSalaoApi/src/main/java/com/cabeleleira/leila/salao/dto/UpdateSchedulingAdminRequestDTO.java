package com.cabeleleira.leila.salao.dto;

import com.cabeleleira.leila.salao.enums.SchedulingOrigin;
import com.cabeleleira.leila.salao.enums.SchedulingStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UpdateSchedulingAdminRequestDTO(
        @NotNull(message = "O status é obrigatório")
        SchedulingStatus status,

        @NotNull(message = "A data e hora do agendamento é obrigatória")
        @Future(message = "A data do agendamento deve ser no futuro")
        LocalDateTime dateHours,

        SchedulingOrigin origin,

        @NotEmpty(message = "É necessário informar pelo menos um serviço")
        List<UUID> servicesIds
) { }

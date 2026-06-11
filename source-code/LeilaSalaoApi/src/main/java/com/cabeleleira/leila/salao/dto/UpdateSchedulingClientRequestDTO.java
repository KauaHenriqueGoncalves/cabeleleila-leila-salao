package com.cabeleleira.leila.salao.dto;

import com.cabeleleira.leila.salao.enums.SchedulingOrigin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UpdateSchedulingClientRequestDTO(
        SchedulingOrigin origin,

        @NotEmpty(message = "É necessário informar pelo menos um serviço")
        List<UUID> servicesIds
) { }

package com.cabeleleira.leila.salao.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SchedulingBetweenDatesDTO(
        @NotNull(message = "O valor de inicio é obrigatório")
        LocalDate start,

        @NotNull(message = "O valor de fim é obrigatório")
        LocalDate end
) { }

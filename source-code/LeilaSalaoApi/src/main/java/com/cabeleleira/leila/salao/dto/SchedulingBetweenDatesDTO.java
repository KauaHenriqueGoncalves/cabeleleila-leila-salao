package com.cabeleleira.leila.salao.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public record SchedulingBetweenDatesDTO(
        @NotNull(message = "O valor de inicio é obrigatório")
        @RequestParam LocalDate start,

        @NotNull(message = "O valor de fim é obrigatório")
        @RequestParam LocalDate end
) { }

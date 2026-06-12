package com.cabeleleira.leila.salao.dto;

import java.util.List;

public record DashboardDTO(
        Long totalSchedulings,
        Long completed,
        Long cancelled,
        Long pending,
        Double estimatedRevenue,
        Long totalClients
) { }

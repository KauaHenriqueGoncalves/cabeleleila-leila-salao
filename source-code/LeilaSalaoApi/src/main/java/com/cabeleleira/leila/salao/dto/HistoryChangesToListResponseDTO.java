package com.cabeleleira.leila.salao.dto;

import com.cabeleleira.leila.salao.domain.HistoryChanges;
import com.cabeleleira.leila.salao.enums.HistoryChangedFor;

import java.time.Instant;

public record HistoryChangesToListResponseDTO(
        HistoryChangedFor changedFor,
        String fieldChanged,
        Double priceBefore,
        Double priceNow,
        Instant createdAt
) {
    public static HistoryChangesToListResponseDTO from(HistoryChanges h) {
        return new HistoryChangesToListResponseDTO(
                h.getChangedFor(),
                h.getFieldChanged(),
                h.getPriceBefore(),
                h.getPriceNow(),
                h.getCreatedAt()
        );
    }
}

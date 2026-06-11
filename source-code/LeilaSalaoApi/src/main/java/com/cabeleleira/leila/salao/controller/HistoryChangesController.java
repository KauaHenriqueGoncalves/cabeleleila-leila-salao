package com.cabeleleira.leila.salao.controller;

import com.cabeleleira.leila.salao.dto.HistoryChangesToListResponseDTO;
import com.cabeleleira.leila.salao.service.interfaces.IHistoryChangesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/history-changes")
public class HistoryChangesController {
    private final IHistoryChangesService historyChangesService;

    public HistoryChangesController(IHistoryChangesService historyChangesService) {
        this.historyChangesService = historyChangesService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<HistoryChangesToListResponseDTO>> findAll(
            @PathVariable UUID id
    ) {
        List<HistoryChangesToListResponseDTO> response = historyChangesService.findAllBySchedulingId(id);
        return ResponseEntity.ok(response);
    }
}

package com.cabeleleira.leila.salao.controller;

import com.cabeleleira.leila.salao.dto.CreateSchedulingRequestDTO;
import com.cabeleleira.leila.salao.dto.SchedulingToListResponseDTO;
import com.cabeleleira.leila.salao.dto.UpdateSchedulingAdminRequestDTO;
import com.cabeleleira.leila.salao.dto.UpdateSchedulingClientRequestDTO;
import com.cabeleleira.leila.salao.service.interfaces.ISchedulingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedulings")
public class SchedulingController {
    private final ISchedulingService schedulingService;

    public SchedulingController(ISchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('admin', 'client')")
    public ResponseEntity<Void> create(
            @RequestBody @Valid CreateSchedulingRequestDTO dto,
            JwtAuthenticationToken token
    ) {
        UUID userId = UUID.fromString(token.getName());
        UUID id = schedulingService.create(dto, userId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<SchedulingToListResponseDTO>> findAll() {
        List<SchedulingToListResponseDTO> response = schedulingService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<List<SchedulingToListResponseDTO>> findAllByUser(
            JwtAuthenticationToken token
    ) {
        UUID userId = UUID.fromString(token.getName());
        List<SchedulingToListResponseDTO> response = schedulingService.findAllByUser(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('client')")
    public ResponseEntity<Void> updateClient(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateSchedulingClientRequestDTO dto
    ) {
        schedulingService.updateClient(id, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> updateAdmin(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateSchedulingAdminRequestDTO dto
    ) {
        schedulingService.updateAdmin(id, dto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> deleteById(
            @PathVariable UUID id
    ) {
        schedulingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

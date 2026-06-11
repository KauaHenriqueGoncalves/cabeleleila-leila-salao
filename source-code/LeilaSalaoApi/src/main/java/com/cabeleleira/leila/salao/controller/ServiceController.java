package com.cabeleleira.leila.salao.controller;

import com.cabeleleira.leila.salao.dto.CreateServiceRequestDTO;
import com.cabeleleira.leila.salao.dto.ServiceToListResponseDTO;
import com.cabeleleira.leila.salao.service.interfaces.IServiceDomainService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/services")
public class ServiceController {
    private final IServiceDomainService serviceDomainService;

    public ServiceController(IServiceDomainService serviceDomainService) {
        this.serviceDomainService = serviceDomainService;
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> createService(
            @RequestBody @Valid CreateServiceRequestDTO dto
    ) {
        UUID id = serviceDomainService.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('admin', 'client')")
    public ResponseEntity<List<ServiceToListResponseDTO>> findAll() {
        List<ServiceToListResponseDTO> response = serviceDomainService.findAll();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> deleteById(
            @PathVariable UUID id
    ) {
        serviceDomainService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

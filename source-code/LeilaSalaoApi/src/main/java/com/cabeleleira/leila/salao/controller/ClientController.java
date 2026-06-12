package com.cabeleleira.leila.salao.controller;

import com.cabeleleira.leila.salao.dto.ClientToListResponseDTO;
import com.cabeleleira.leila.salao.dto.CreateClientRequestDTO;
import com.cabeleleira.leila.salao.service.interfaces.IClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Void> createClient(
            @RequestBody @Valid CreateClientRequestDTO dto
    ) {
        UUID id = clientService.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<ClientToListResponseDTO>> findAllClientToList() {
        List<ClientToListResponseDTO> response = clientService.findAll();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> deleteById(
            @PathVariable UUID id
    ) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

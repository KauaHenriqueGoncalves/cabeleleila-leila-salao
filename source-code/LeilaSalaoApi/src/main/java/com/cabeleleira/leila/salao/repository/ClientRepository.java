package com.cabeleleira.leila.salao.repository;

import com.cabeleleira.leila.salao.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}

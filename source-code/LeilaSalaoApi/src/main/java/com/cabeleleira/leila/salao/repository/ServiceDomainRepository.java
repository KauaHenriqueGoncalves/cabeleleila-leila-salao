package com.cabeleleira.leila.salao.repository;

import com.cabeleleira.leila.salao.domain.ServiceDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceDomainRepository extends JpaRepository<ServiceDomain, UUID> {
}

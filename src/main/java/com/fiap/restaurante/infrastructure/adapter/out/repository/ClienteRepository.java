package com.fiap.restaurante.infrastructure.adapter.out.repository;

import com.fiap.restaurante.infrastructure.adapter.out.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findFirstByCpf(String cpf);

}

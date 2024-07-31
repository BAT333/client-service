package com.example.service.client.repository;

import com.example.service.client.domain.Client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    Page<Client> findByActiveTrue(Pageable pageable);

    boolean existsByIdAndActiveTrue(Long id);

    Optional<Client> findByIdAndActiveTrue(Long id);
}

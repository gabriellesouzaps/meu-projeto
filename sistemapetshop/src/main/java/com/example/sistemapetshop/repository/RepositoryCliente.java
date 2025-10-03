package com.example.sistemapetshop.repository;

import com.example.sistemapetshop.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepositoryCliente extends JpaRepository<Cliente, Long> {
    // Derived Query 1: buscar clientes por nome (contendo)
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}

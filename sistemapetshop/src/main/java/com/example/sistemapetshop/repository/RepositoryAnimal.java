package com.example.sistemapetshop.repository;


import com.example.sistemapetshop.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepositoryAnimal extends JpaRepository<Animal, Long> {
    // Derived Query 2: buscar animais por raça
    List<Animal> findByRaca(String raca);

    // Derived Query adicional: buscar animais por parte do nome
    List<Animal> findByNomeContainingIgnoreCase(String nome);
}

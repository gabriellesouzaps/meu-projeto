package com.example.sistemapetshop.repository;


import com.example.sistemapetshop.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryAtendimento extends JpaRepository<Atendimento, Long> {

    // JPQL 1: atendimentos de um cliente (via animal -> cliente)
    @Query("SELECT a FROM Atendimento a WHERE a.animal.cliente.id = :idCliente")
    List<Atendimento> buscarAtendimentosPorCliente(@Param("idCliente") Long idCliente);

    // JPQL 2: atendimentos entre duas datas
    @Query("SELECT a FROM Atendimento a WHERE a.dataAtendimento BETWEEN :inicio AND :fim")
    List<Atendimento> buscarAtendimentosEntreDatas(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    // nativeQuery: buscar por texto na descrição (exemplo)
    @Query(value = "SELECT * FROM atendimento WHERE descricao LIKE %:texto%", nativeQuery = true)
    List<Atendimento> buscarPorDescricaoNative(@Param("texto") String texto);
}

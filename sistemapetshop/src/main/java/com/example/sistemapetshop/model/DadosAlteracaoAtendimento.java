package com.example.sistemapetshop.model;

import java.time.LocalDate;

public record DadosAlteracaoAtendimento(Long id, LocalDate dataAtendimento, String descricao,String status, Long idAnimal) {
}

package com.example.sistemapetshop.model;

import java.time.LocalDate;

public record DadosCadastroAtendimento(LocalDate dataAtendimento, String descricao,String status, Long idAnimal) {
}

package com.example.sistemapetshop.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "atendimento")
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataAtendimento;
    private String descricao;
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_animal")
    private Animal animal;

    public Atendimento() {}

    public Atendimento(DadosCadastroAtendimento dados, Animal animal) {
        this.dataAtendimento = dados.dataAtendimento();
        this.descricao = dados.descricao();
        this.status = dados.status();
        this.animal = animal;
    }

    public Long getId() { return id; }
    public LocalDate getDataAtendimento() { return dataAtendimento; }
    public String getDescricao() { return descricao; }
    public Animal getAnimal() { return animal; }
    public String getStatus(){return status;}

    public void atualizarDados(DadosAlteracaoAtendimento dados, Animal animal) {
        this.dataAtendimento = dados.dataAtendimento();
        this.descricao = dados.descricao();
        this.animal = animal;
        this.status = dados.status();
    }
}

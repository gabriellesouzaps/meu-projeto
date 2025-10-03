package com.example.sistemapetshop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String raca;
    private Integer idade;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atendimento> listaAtendimentos = new ArrayList<>();

    public Animal() {}

    public Animal(DadosCadastroAnimal dados, Cliente cliente) {
        this.nome = dados.nome();
        this.raca = dados.raca();
        this.idade = dados.idade();
        this.cliente = cliente;
    }

    // getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getRaca() { return raca; }
    public Integer getIdade() { return idade; }
    public Cliente getCliente() { return cliente; }
    public List<Atendimento> getListaAtendimentos() { return listaAtendimentos; }

    public void atualizarDados(DadosAlteracaoAnimal dados, Cliente cliente) {
        this.nome = dados.nome();
        this.raca = dados.raca();
        this.idade = dados.idade();
        this.cliente = cliente;
    }
}

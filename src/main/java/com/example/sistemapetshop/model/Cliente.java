package com.example.sistemapetshop.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> listaAnimal = new ArrayList<>();

    public Cliente() {}

    public Cliente(DadosCadastroCliente dados) {
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.email = dados.email();
    }

    // getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public List<Animal> getListaAnimal() { return listaAnimal; }

    public void atualizarDados(DadosAlteracaoCliente dados) {
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.email = dados.email();
    }
}

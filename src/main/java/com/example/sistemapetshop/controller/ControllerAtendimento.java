package com.example.sistemapetshop.controller;

import com.example.sistemapetshop.model.*;
import com.example.sistemapetshop.repository.RepositoryAtendimento;
import com.example.sistemapetshop.repository.RepositoryAnimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Controller // Indica que esta classe é um Controller do Spring MVC
@RequestMapping("/atendimentos") // Todas as rotas começam com /atendimentos
public class ControllerAtendimento {

    @Autowired // Injeta o repositório de atendimentos
    private RepositoryAtendimento repositoryAtendimento;

    @Autowired // Injeta o repositório de animais
    private RepositoryAnimal repositoryAnimal;

    // ========================= FORMULÁRIO DE CADASTRO/EDIÇÃO =========================
    @GetMapping("/formulario")
    public String carregaFormulario(Long id, Model model) {
        // Adiciona a lista de animais para popular o campo de seleção no formulário
        model.addAttribute("listaAnimal", repositoryAnimal.findAll());

        // Caso seja passado um ID, significa que é edição
        if (id != null) {
            Atendimento a = repositoryAtendimento.getReferenceById(id);
            model.addAttribute("atendimento", a); // Preenche o formulário com os dados existentes
        }

        // Retorna a view "atendimentos/formulario.html"
        return "atendimentos/formulario";
    }

    // ========================= CADASTRAR NOVO ATENDIMENTO =========================
    @PostMapping("/formulario")
    public String cadastraAtendimento(DadosCadastroAtendimento dados) {
        // Recupera o animal relacionado ao atendimento
        var animal = repositoryAnimal.getReferenceById(dados.idAnimal());

        // Cria um novo atendimento a partir dos dados e do animal
        Atendimento a = new Atendimento(dados, animal);

        // Salva no banco
        repositoryAtendimento.save(a);

        // Redireciona para a listagem
        return "redirect:/atendimentos/listagem";
    }

    // ========================= LISTAGEM DE ATENDIMENTOS =========================
    @GetMapping("/listagem")
    public String carregaListagem(Model model) {
        // Lista todos os atendimentos
        model.addAttribute("lista", repositoryAtendimento.findAll());

        // Cria uma lista de clientes únicos (pegando de cada animal cadastrado)
        model.addAttribute("listaCliente", repositoryAnimal.findAll()
                .stream()
                .map(a -> a.getCliente())
                .distinct()
                .toList());

        // Retorna a página "atendimentos/listagem.html"
        return "atendimentos/listagem";
    }

    // ========================= REMOVER ATENDIMENTO =========================
    @DeleteMapping("/{id}") // DELETE em /atendimentos/{id}
    @Transactional // Garante execução dentro de transação
    public String removeAtendimento(@PathVariable Long id) {
        // Exclui o atendimento pelo ID
        repositoryAtendimento.deleteById(id);

        // Volta para a listagem
        return "redirect:/atendimentos/listagem";
    }

    // ========================= ALTERAR ATENDIMENTO EXISTENTE =========================
    @PutMapping("/formulario")
    @Transactional
    public String alteraAtendimento(DadosAlteracaoAtendimento dados) {
        // Recupera o atendimento existente
        Atendimento a = repositoryAtendimento.getReferenceById(dados.id());

        // Recupera o animal associado
        var animal = repositoryAnimal.getReferenceById(dados.idAnimal());

        // Atualiza os dados
        a.atualizarDados(dados, animal);

        // Redireciona para listagem
        return "redirect:/atendimentos/listagem";
    }

    // ========================= CONSULTA 1: Buscar por Cliente =========================
    @GetMapping("/buscar/cliente")
    public String buscarPorCliente(@RequestParam Long idCliente, Model model) {
        // Busca atendimentos de um cliente específico (JPQL ou Derived Query)
        model.addAttribute("lista", repositoryAtendimento.buscarAtendimentosPorCliente(idCliente));

        // Lista de clientes distintos para o filtro
        model.addAttribute("listaCliente", repositoryAnimal.findAll()
                .stream()
                .map(a -> a.getCliente())
                .distinct()
                .toList());

        return "atendimentos/listagem";
    }

    // ========================= CONSULTA 2: Buscar entre Datas =========================
    @GetMapping("/buscar/entre")
    public String buscarEntreDatas(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
                                   Model model) {
        // Se as duas datas forem passadas, busca no intervalo
        if (inicio != null && fim != null) {
            model.addAttribute("lista", repositoryAtendimento.buscarAtendimentosEntreDatas(inicio, fim));
        } else {
            // Caso contrário, retorna todos
            model.addAttribute("lista", repositoryAtendimento.findAll());
        }

        // Lista de clientes distintos para filtros
        model.addAttribute("listaCliente", repositoryAnimal.findAll()
                .stream()
                .map(a -> a.getCliente())
                .distinct()
                .toList());

        return "atendimentos/listagem";
    }

    // ========================= CONSULTA 3: Buscar por Descrição =========================
    @GetMapping("/buscar/descricao")
    public String buscarPorDescricao(@RequestParam String texto, Model model) {
        // Busca atendimentos que contenham o texto na descrição (nativeQuery)
        model.addAttribute("lista", repositoryAtendimento.buscarPorDescricaoNative(texto));

        // Lista de clientes distintos
        model.addAttribute("listaCliente", repositoryAnimal.findAll()
                .stream()
                .map(a -> a.getCliente())
                .distinct()
                .toList());

        return "atendimentos/listagem";
    }
}

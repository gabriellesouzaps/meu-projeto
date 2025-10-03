package com.example.sistemapetshop.controller;

import com.example.sistemapetshop.model.*;
import com.example.sistemapetshop.repository.RepositoryCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/clientes")
public class ControllerCliente {

    @Autowired // Injeta a dependência do repositório de clientes
    private RepositoryCliente repositoryCliente;

    // ========================= FORMULÁRIO DE CADASTRO/EDIÇÃO =========================
    @GetMapping("/formulario")
    public String carregaFormulario(Long id, Model model) {
        // Se foi passado um id, significa que é edição (carrega cliente do banco)
        if (id != null) {
            Cliente cliente = repositoryCliente.getReferenceById(id);
            model.addAttribute("cliente", cliente); // Passa o cliente para preencher no form
        }

        // Retorna a view "clientes/formulario.html"
        return "clientes/formulario";
    }

    // ========================= CADASTRAR NOVO CLIENTE =========================
    @PostMapping("/formulario")
    public String cadastraCliente(DadosCadastroCliente dados) {
        // Cria um novo cliente a partir dos dados recebidos
        Cliente c = new Cliente(dados);

        // Salva no banco
        repositoryCliente.save(c);

        // Redireciona para a listagem de clientes
        return "redirect:/clientes/listagem";
    }

    // ========================= LISTAGEM DE CLIENTES =========================
    @GetMapping("/listagem")
    public String carregaListagem(Model model, String busca) {
        // Se foi informado um texto de busca, pesquisa clientes pelo nome (ignore case)
        if (busca != null && !busca.isBlank())
            model.addAttribute("lista", repositoryCliente.findByNomeContainingIgnoreCase(busca));
        else
            // Caso contrário, lista todos os clientes
            model.addAttribute("lista", repositoryCliente.findAll());

        // Retorna a página "clientes/listagem.html"
        return "clientes/listagem";
    }

    // ========================= REMOVER CLIENTE =========================
    @DeleteMapping("/{id}") // DELETE em /clientes/{id}
    @Transactional // Garante que a exclusão aconteça dentro de uma transação
    public String removeCliente(@PathVariable Long id) {
        // Remove o cliente pelo ID
        repositoryCliente.deleteById(id);

        // Redireciona para a listagem
        return "redirect:/clientes/listagem";
    }

    // ========================= ALTERAR CLIENTE EXISTENTE =========================
    @PutMapping("/formulario")
    @Transactional
    public String alteraCliente(DadosAlteracaoCliente dados) {
        // Busca o cliente existente no banco
        Cliente c = repositoryCliente.getReferenceById(dados.id());

        // Atualiza os dados do cliente com as informações recebidas
        c.atualizarDados(dados);

        // Redireciona para a listagem
        return "redirect:/clientes/listagem";
    }
}

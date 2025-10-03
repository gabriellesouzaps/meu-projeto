package com.example.sistemapetshop.controller;

import com.example.sistemapetshop.model.*;
import com.example.sistemapetshop.repository.RepositoryAnimal;
import com.example.sistemapetshop.repository.RepositoryCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/animais")
public class ControllerAnimal {

    @Autowired // Injeta a dependência do repositório de Animal
    private RepositoryAnimal repositoryAnimal;

    @Autowired // Injeta a dependência do repositório de Cliente
    private RepositoryCliente repositoryCliente;

    // ========================= FORMULÁRIO DE CADASTRO/EDIÇÃO =========================
    @GetMapping("/formulario")
    public String carregaFormulario(Long id, Model model) {
        // Adiciona a lista de clientes para exibir no select do formulário
        model.addAttribute("listaCliente", repositoryCliente.findAll());

        // Se foi passado um id, significa que estamos editando um animal existente
        if (id != null) {
            Animal a = repositoryAnimal.getReferenceById(id);
            model.addAttribute("animal", a); // Preenche os campos com os dados do animal
        }

        // Retorna a view "animais/formulario.html"
        return "animais/formulario";
    }

    // ========================= CADASTRAR NOVO ANIMAL =========================
    @PostMapping("/formulario")
    public String cadastraAnimal(DadosCadastroAnimal dados) {
        // Recupera o cliente relacionado ao animal
        var cliente = repositoryCliente.getReferenceById(dados.idCliente());

        // Cria novo objeto Animal com os dados do formulário
        Animal a = new Animal(dados, cliente);

        // Salva no banco de dados
        repositoryAnimal.save(a);

        // Redireciona para a tela de listagem
        return "redirect:/animais/listagem";
    }

    // ========================= LISTAGEM DE ANIMAIS =========================
    @GetMapping("/listagem")
    public String carregaListagem(Model model, String raca, String busca) {
        // Se foi passado parâmetro "raca", filtra pela raça
        if (raca != null && !raca.isBlank())
            model.addAttribute("lista", repositoryAnimal.findByRaca(raca));

            // Se foi passado parâmetro "busca", filtra pelo nome do animal
        else if (busca != null && !busca.isBlank())
            model.addAttribute("lista", repositoryAnimal.findByNomeContainingIgnoreCase(busca));

            // Caso contrário, traz todos os animais
        else
            model.addAttribute("lista", repositoryAnimal.findAll());

        // Retorna a página de listagem "animais/listagem.html"
        return "animais/listagem";
    }

    // ========================= REMOVER ANIMAL =========================
    @DeleteMapping("/{id}") // DELETE em /animais/{id}
    @Transactional // Garante que a operação de exclusão seja feita dentro de uma transação
    public String removeAnimal(@PathVariable Long id) {
        // Exclui o animal pelo ID
        repositoryAnimal.deleteById(id);

        // Redireciona para a listagem
        return "redirect:/animais/listagem";
    }

    // ========================= ALTERAR ANIMAL EXISTENTE =========================
    @PutMapping("/formulario")
    @Transactional
    public String alteraAnimal(DadosAlteracaoAnimal dados) {
        // Busca o animal existente no banco
        Animal a = repositoryAnimal.getReferenceById(dados.id());

        // Recupera o cliente associado ao animal
        var cliente = repositoryCliente.getReferenceById(dados.idCliente());

        // Atualiza os dados do animal
        a.atualizarDados(dados, cliente);

        // Redireciona para listagem
        return "redirect:/animais/listagem";
    }
}

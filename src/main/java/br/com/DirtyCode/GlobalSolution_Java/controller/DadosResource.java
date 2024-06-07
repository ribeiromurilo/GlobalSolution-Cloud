package br.com.DirtyCode.GlobalSolution_Java.controller;

import br.com.DirtyCode.GlobalSolution_Java.model.Dados;
import br.com.DirtyCode.GlobalSolution_Java.repository.DadosRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/dados")
@Slf4j
public class DadosResource {

    @Autowired
    DadosRepository repository;

    @PostMapping
    @Operation(
            summary = "Cadastrar Dados",
            description = "Cadastra um dado especificado.",
            tags = "Cadastro de Dados"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dado criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique o corpo da requisição.")
    })
    public ResponseEntity<String> save(@Valid @RequestBody Dados dados){
        Dados save = repository.save(dados);
        log.info("Dado cadastrado: " + dados);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Dado cadastrado com sucesso!");
    }

    @GetMapping(value = "/todos")
    @Operation(
            summary = "Listar Dados",
            description = "Retornar um array com todos os dados registrados",
            tags = "Listar todos dados"
    )
    public List<Dados> findAll() {
        List<Dados> lista = repository.findAll();
        for (Dados i : lista) {
            i.add(linkTo(methodOn(DadosResource.class).findById(i.getId()))
                    .withRel("Quer buscar o dado por id? Acesse este serviço: "));
            i.add(linkTo(methodOn(DadosResource.class).save(new Dados()))
                    .withRel("Quer adicionar um novo dado? Acesse este serviço (via POST Request): "));
        }
        return lista;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Listar Dados pelo ID",
            description = "Retornar um dado pelo seu ID",
            tags = "Listar dado por ID"
    )
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Dados> dado = repository.findById(id);
        if (dado.isPresent()) {
            dado.get().add(linkTo(methodOn(DadosResource.class).findAll()).withRel("Quer acessar todos os dados? Utilize este link: "));
            return ResponseEntity.ok(dado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dado não encontrado.");
        }
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Deletar Dado",
            description = "Deletar um dado especificado através de seu ID.",
            tags = "Deletar dado"
    )
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Dado deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dado não encontrado.");
        }
    }

    @Operation(
            summary = "Atualizar Dado",
            description = "Atualizar os dados de um dado especificado através de seu ID.",
            tags = "Atualizar dado"
    )
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Dados dados) {
        boolean exists = repository.existsById(id);
        if (exists) {
            dados.setId(id);
            Dados updatedDado = repository.save(dados);
            return ResponseEntity.ok(updatedDado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dado não encontrado.");
        }
    }
}

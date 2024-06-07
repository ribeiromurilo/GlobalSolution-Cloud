package br.com.DirtyCode.GlobalSolution_Java.controller;

import br.com.DirtyCode.GlobalSolution_Java.model.Relatorios;
import br.com.DirtyCode.GlobalSolution_Java.repository.RelatorioRepository;
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
@RequestMapping(value = "/relatorios")
@Slf4j
public class RelatorioResource {
    @Autowired
    RelatorioRepository repository;

    @PostMapping
    @Operation(
            summary = "Cadastrar Relatórios",
            description = "Cadastra um relatório especificado através de seu ID.",
            tags = "Cadastro de Relatório"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Relatório criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique o corpo da requisição.")
    })
    public ResponseEntity<String> save(@Valid @RequestBody Relatorios relatorio){
        Relatorios save = repository.save(relatorio);
        log.info("Relatório cadastrado " + relatorio);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Relatório cadastrado com sucesso!");
    }

    @GetMapping(value = "/todos")
    @Operation(
            summary = "Listar Relatórios",
            description = "Retornar um array com todos os relatórios registrados",
            tags = "Listar todos relatórios"
    )
    public List<Relatorios> findAll() {
        List<Relatorios> lista = repository.findAll();
        for (Relatorios i : lista) {
            i.add(linkTo(methodOn(RelatorioResource.class).findById(i.getId()))
                    .withRel("Quer buscar o relatório por id? Acesse este serviço: "));
            i.add(linkTo(methodOn(RelatorioResource.class).save(new Relatorios()))
                    .withRel("Quer adicionar um novo relatório? Acesse este serviço (via POST Request): "));
        }
        return lista;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Listar Relatórios pelo ID",
            description = "Retornar um relatório pelo seu ID",
            tags = "Listar relatório por ID"
    )
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Relatorios> relatorio = repository.findById(id);
        if (relatorio.isPresent()) {
            relatorio.get().add(linkTo(methodOn(RelatorioResource.class).findAll()).withRel("Quer acessar todos os relatórios? Utilize este link: "));
            return ResponseEntity.ok(relatorio.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Relatório não encontrado.");
        }
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Deletar Relatório",
            description = "Deletar um relatório especificado através de seu ID.",
            tags = "Deletar relatório"
    )
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Relatório deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Relatório não encontrado.");
        }
    }

    @Operation(
            summary = "Atualizar Relatório",
            description = "Atualizar os dados de um relatório especificado através de seu ID.",
            tags = "Atualizar relatório"
    )
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Relatorios relatorio) {
        boolean exists = repository.existsById(id);
        if (exists) {
            relatorio.setId(id);
            Relatorios updatedRelatorio = repository.save(relatorio);
            return ResponseEntity.ok(updatedRelatorio);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Relatório não encontrado.");
        }
    }
}

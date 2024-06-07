package br.com.DirtyCode.GlobalSolution_Java.controller;

import br.com.DirtyCode.GlobalSolution_Java.model.Alerta;
import br.com.DirtyCode.GlobalSolution_Java.repository.AlertaRepository;
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
@RequestMapping("/alertas")
@Slf4j
public class AlertasResource {

    @Autowired
    private AlertaRepository repository;

    @PostMapping
    @Operation(
            summary = "Cadastrar Alerta",
            description = "Cadastra um alerta especificado.",
            tags = "Cadastro de Alerta"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique o corpo da requisição.")
    })
    public ResponseEntity<String> save(@Valid @RequestBody Alerta alerta) {
        Alerta savedAlerta = repository.save(alerta);
        log.info("Alerta cadastrado: " + savedAlerta);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Alerta cadastrado com sucesso!");
    }

    @GetMapping("/todos")
    @Operation(
            summary = "Listar Alertas",
            description = "Retornar um array com todos os alertas registrados",
            tags = "Listar todos alertas"
    )
    public List<Alerta> findAll() {
        List<Alerta> lista = repository.findAll();
        for (Alerta alerta : lista) {
            alerta.add(linkTo(methodOn(AlertasResource.class).findById(alerta.getId()))
                    .withRel("Quer buscar o alerta por id? Acesse este serviço: "));
            alerta.add(linkTo(methodOn(AlertasResource.class).save(new Alerta()))
                    .withRel("Quer adicionar um novo alerta? Acesse este serviço (via POST Request): "));
        }
        return lista;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Listar Alertas pelo ID",
            description = "Retornar um alerta pelo seu ID",
            tags = "Listar alerta por ID"
    )
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Alerta> alerta = repository.findById(id);
        if (alerta.isPresent()) {
            alerta.get().add(linkTo(methodOn(AlertasResource.class).findAll()).withRel("Quer acessar todos os alertas? Utilize este link: "));
            return ResponseEntity.ok(alerta.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Alerta não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar Alerta",
            description = "Deletar um alerta especificado através de seu ID.",
            tags = "Deletar alerta"
    )
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Alerta deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alerta não encontrado.");
        }
    }

    @Operation(
            summary = "Atualizar Alerta",
            description = "Atualizar os dados de um alerta especificado através de seu ID.",
            tags = "Atualizar alerta"
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Alerta alerta) {
        boolean exists = repository.existsById(id);
        if (exists) {
            alerta.setId(id);
            Alerta updatedAlerta = repository.save(alerta);
            return ResponseEntity.ok(updatedAlerta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alerta não encontrado.");
        }
    }
}

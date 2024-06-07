package br.com.DirtyCode.GlobalSolution_Java.controller;
import br.com.DirtyCode.GlobalSolution_Java.model.Sensores;
import br.com.DirtyCode.GlobalSolution_Java.repository.SensoresRepository;
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
@RequestMapping("/sensores")
@Slf4j
public class SensoresResource {

    @Autowired
    private SensoresRepository repository;

    @PostMapping
    @Operation(
            summary = "Cadastrar Sensor",
            description = "Cadastra um sensor especificado.",
            tags = "Cadastro de Sensor"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sensor criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique o corpo da requisição.")
    })
    public ResponseEntity<String> save(@Valid @RequestBody Sensores sensor) {
        Sensores savedSensor = repository.save(sensor);
        log.info("Sensor cadastrado: " + savedSensor);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Sensor cadastrado com sucesso!");
    }

    @GetMapping("/todos")
    @Operation(
            summary = "Listar Sensores",
            description = "Retornar um array com todos os sensores registrados",
            tags = "Listar todos sensores"
    )
    public List<Sensores> findAll() {
        List<Sensores> lista = repository.findAll();
        for (Sensores sensor : lista) {
            sensor.add(linkTo(methodOn(SensoresResource.class).findById(sensor.getId()))
                    .withRel("Quer buscar o sensor por id? Acesse este serviço: "));
            sensor.add(linkTo(methodOn(SensoresResource.class).save(new Sensores()))
                    .withRel("Quer adicionar um novo sensor? Acesse este serviço (via POST Request): "));
        }
        return lista;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Listar Sensores pelo ID",
            description = "Retornar um sensor pelo seu ID",
            tags = "Listar sensor por ID"
    )
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Sensores> sensor = repository.findById(id);
        if (sensor.isPresent()) {
            sensor.get().add(linkTo(methodOn(SensoresResource.class).findAll()).withRel("Quer acessar todos os sensores? Utilize este link: "));
            return ResponseEntity.ok(sensor.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sensor não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar Sensor",
            description = "Deletar um sensor especificado através de seu ID.",
            tags = "Deletar sensor"
    )
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Sensor deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor não encontrado.");
        }
    }

    @Operation(
            summary = "Atualizar Sensor",
            description = "Atualizar os dados de um sensor especificado através de seu ID.",
            tags = "Atualizar sensor"
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Sensores sensor) {
        boolean exists = repository.existsById(id);
        if (exists) {
            sensor.setId(id);
            Sensores updatedSensor = repository.save(sensor);
            return ResponseEntity.ok(updatedSensor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor não encontrado.");
        }
    }
}

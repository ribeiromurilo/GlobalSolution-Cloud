package br.com.DirtyCode.GlobalSolution_Java.controller;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import br.com.DirtyCode.GlobalSolution_Java.model.Usuario;
import br.com.DirtyCode.GlobalSolution_Java.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UsuarioResource {

    @Autowired
    UsuarioRepository repository;

    @PostMapping
    @Operation(
            summary = "Cadastrar Usuários",
            description = "Cadastra um usuário especificado através de seu ID.",
            tags = "Cadastro de Usuario"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique o corpo da requisição.")
    })
    public ResponseEntity<String> save(@Valid @RequestBody Usuario user){
        Usuario save = repository.save(user);
        log.info("Usuario cadastrado "+ user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário cadastrado com sucesso!");
    }

    @GetMapping(value = "/todas")
    @Operation(
            summary = "Listar Usuarios",
            description = "Retornar um array com todos os usuarios registrados",
            tags = "Listar todos usuarios"
    )
    public List<Usuario> findAll() {
        List<Usuario> lista = repository.findAll();
        for (Usuario i : lista) {
            i.add(linkTo(methodOn(UsuarioResource.class).findById(i.getIdUsuario()))
                    .withRel("Quer buscar o usuário por id? Acesse este serviço: "));
            i.add(linkTo(methodOn(UsuarioResource.class).save(new Usuario()))
                    .withRel("Quer adicionar um novo usuário? Acesse este serviço (via POST Request): "));
        }
        return lista;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Listar Usuarios pelo ID",
            description = "Retornar um Usuario pelo seu ID",
            tags = "Listar usuario por ID"
    )
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isPresent()) {
            usuario.get().add(linkTo(methodOn(UsuarioResource.class).findAll()).withRel("Quer acessar todas as músicas?" +
                    " Utilize este link: "));

            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado.");
        }
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Deletar usuario",
            description = "Deletar um usuário especificado através de seu ID.",
            tags = "Deletar usuario"
    )
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }
    @Operation(
            summary = "Atualizar usuario",
            description = "Atualizar os dados de um usuário especificado através de seu ID.",
            tags = "Atualizar usuario"
    )
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        boolean exists = repository.existsById(id);
        if (exists) {
            usuario.setIdUsuario(id);
            Usuario updatedUsuario = repository.save(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }
    @Operation(
            summary = "Listar usuario validando Email/Senha",
            description = "Listar um usuário especificado através do seu email.",
            tags = "Listar usuario pelo email/senha"
    )
    @PostMapping("/usuario")
    public ResponseEntity<?> findByEmail(@RequestBody Usuario user) {
        String email = user.getEmail();
        Usuario usuario = repository.findByEmail(email);
        if (usuario != null) {
            if(user.getSenha().equals(usuario.getSenha())) {
                return ResponseEntity.ok(usuario);
            }else {
                return ResponseEntity.status(404).body("Email ou senha incorreto!");
            }
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }
}

package br.com.DirtyCode.GlobalSolution_Java.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "Usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends RepresentationModel<Usuario> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotBlank(message = "O nome é obrigatório.")
    @Column(name = "nm_usuario", nullable = false)
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Column(name = "email_usuario", nullable = false, unique = true)
    @Email
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Column(name = "senha_usuario", nullable = false)
    private String senha;

    @NotBlank(message = "O tipo de usuário é obrigatório.")
    @Column(name = "tipo_usuario", nullable = false)
    private String tipo;

    @Column(name = "dt_usuario", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        data = Date.from(zonedDateTime.toInstant());
    }
}

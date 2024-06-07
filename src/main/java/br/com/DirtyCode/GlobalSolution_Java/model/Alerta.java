package br.com.DirtyCode.GlobalSolution_Java.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
@Entity
@Table(name = "Alertas")
@AllArgsConstructor
@NoArgsConstructor
public class Alerta extends RepresentationModel<Alerta> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long id;

    @NotBlank(message = "O tipo do alerta é obrigatório.")
    @Column(name = "tipo_alerta", nullable = false)
    private String tipo;

    @NotBlank(message = "A mensagem do alerta é obrigatória.")
    @Column(name = "msg_alerta", nullable = false)
    private String mensagem;

    @NotNull(message = "A data do alerta é obrigatória.")
    @PastOrPresent(message = "A data do alerta deve ser no passado ou presente.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_alerta", nullable = false)
    private Date data;
}

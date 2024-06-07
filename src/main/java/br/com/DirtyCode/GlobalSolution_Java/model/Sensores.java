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
@Table(name = "Sensores")
@AllArgsConstructor
@NoArgsConstructor
public class Sensores extends RepresentationModel<Sensores> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensor")
    private Long id;

    @NotBlank(message = "O nome do sensor é obrigatório.")
    @Column(name = "nm_sensor", nullable = false)
    private String nome;

    @NotBlank(message = "O tipo do sensor é obrigatório.")
    @Column(name = "tipo_sensor", nullable = false)
    private String tipo;

    @NotBlank(message = "A localização do sensor é obrigatória.")
    @Column(name = "loc_sensor", nullable = false)
    private String localizacao;

    @NotNull(message = "A data é obrigatória.")
    @PastOrPresent(message = "A data deve ser no passado ou presente.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_sensor", nullable = false)
    private Date data;
}
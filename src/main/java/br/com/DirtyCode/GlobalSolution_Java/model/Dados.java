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
@Table(name = "Dados")
@AllArgsConstructor
@NoArgsConstructor
public class Dados extends RepresentationModel<Dados> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dados")
    private Long id;

    @NotNull(message = "A data é obrigatória.")
    @PastOrPresent(message = "A data deve ser no passado ou presente.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_dados", nullable = false)
    private Date data;

    @NotBlank(message = "O tipo é obrigatório.")
    @Column(name = "tipo_dados", nullable = false)
    private String tipo;

    @NotNull(message = "O valor é obrigatório.")
    @Column(name = "valor_dados", nullable = false)
    private Double valor;
}

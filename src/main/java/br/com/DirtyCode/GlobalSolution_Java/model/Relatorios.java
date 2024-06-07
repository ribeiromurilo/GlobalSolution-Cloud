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
@Table(name = "Relatorios")
@AllArgsConstructor
@NoArgsConstructor
public class Relatorios extends RepresentationModel<Relatorios> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relatorio")
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Column(name = "nm_relatorio", nullable = false)
    private String nome;

    @NotBlank(message = "A descrição é obrigatória.")
    @Column(name = "desc_relatorio", nullable = false)
    private String descricao;

    @NotNull(message = "A data é obrigatória.")
    @PastOrPresent(message = "A data deve ser no passado ou presente.")
    @Column(name = "dt_relatorio", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
}

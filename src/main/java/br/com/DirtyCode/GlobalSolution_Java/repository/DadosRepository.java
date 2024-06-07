package br.com.DirtyCode.GlobalSolution_Java.repository;

import br.com.DirtyCode.GlobalSolution_Java.model.Dados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosRepository extends JpaRepository<Dados, Long> {
}

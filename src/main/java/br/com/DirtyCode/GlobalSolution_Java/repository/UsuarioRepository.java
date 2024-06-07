package br.com.DirtyCode.GlobalSolution_Java.repository;

import br.com.DirtyCode.GlobalSolution_Java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
}

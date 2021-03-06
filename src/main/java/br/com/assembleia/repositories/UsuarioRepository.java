
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByLogin(@Param("login") String login, @Param("senha") String senha);

    List<Usuario> listarPorIgreja(@Param("idIgreja") Long idIgreja);
}

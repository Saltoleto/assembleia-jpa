
package br.com.assembleia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.assembleia.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>
{
    Usuario findByLogin(String login, String senha);
}

/**
 * 
 */
package br.com.assembleia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.assembleia.entities.Usuario;

/**
 * @author Siva
 *
 */
public interface UserRepository extends JpaRepository<Usuario, Integer>
{

}

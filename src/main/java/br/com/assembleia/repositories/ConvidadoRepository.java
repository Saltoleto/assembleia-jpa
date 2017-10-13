
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Convidado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConvidadoRepository extends JpaRepository<Convidado, Integer>
{

}

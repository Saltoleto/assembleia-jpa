
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CargoRepository extends JpaRepository<Cargo, Integer>
{

}
